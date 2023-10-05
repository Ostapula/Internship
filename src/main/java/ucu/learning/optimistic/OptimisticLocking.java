package ucu.learning.optimistic;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static ucu.learning.optimistic.App.allBankAccount;
import static ucu.learning.optimistic.App.deleteAllPersonnel;
import static ucu.learning.optimistic.App.allPersons;
import static ucu.learning.optimistic.App.insertBankAccount;
import static ucu.learning.optimistic.App.insertPerson;
import static ucu.learning.optimistic.App.mkDate;
import static ucu.learning.optimistic.App.Result;
import static ucu.learning.optimistic.App.updateAmountOnAccount;
import static ucu.learning.optimistic.App.insertTransfer;
import static ucu.learning.optimistic.App.BankAccount;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

public class OptimisticLocking {
	public static int updateAmountOnAccountOptimisticaly(final Long account, final BigDecimal amount,
			final Long version, final Connection conn) {
		try (final PreparedStatement ps = conn.prepareStatement(
				"update myschema.bankaccount_ set amount_ = ?, _version = _version + 1 where _id = ? and _version = ?")) {
			ps.setBigDecimal(1, amount);
			ps.setLong(2, account);
			ps.setLong(3, version);
			return ps.executeUpdate();
		} catch (final Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public static Result<Long> transfer(final BankAccount fromAccount, final BankAccount toAccount,
			final BigDecimal amount, final Connection conn) {
		try {
			if (false == conn.getAutoCommit()) {
				return Result.failure("Cannot be executed in the scope of another transaction.");
			}
			conn.setAutoCommit(false);

			// 1: update fromAccount with fromAccount.amount = fromAccount.amount - amount
			final Optional<BigDecimal> opFromAmount = bankAccountAmount(fromAccount.id, fromAccount.version, conn);
			if (opFromAmount.isPresent()) {
				final BigDecimal fromAmount = opFromAmount.get();
				if (1 != updateAmountOnAccountOptimisticaly(fromAccount.id, fromAmount.subtract(amount),
						fromAccount.version, conn)) {
					return Result.failure(format("Could not update the amount for account [%s]", fromAccount));
				}
			} else {
				return Result.failure(format("Could not get thr amount for account [%s]", fromAccount));
			}

			// 2: update toAccount with toAccount.amount = toAccount.amount + amount
			final Optional<BigDecimal> opToAmount = bankAccountAmount(toAccount.id, toAccount.version, conn);
			if (opToAmount.isPresent()) {
				final BigDecimal toAmount = opToAmount.get();
				if (1 != updateAmountOnAccountOptimisticaly(toAccount.id, toAmount.add(amount), toAccount.version,
						conn)) {
					return Result.failure(format("Could not update the amount for account [%s]", toAccount));
				}
			} else {
				return Result.failure(format("Could not get the amount for account [%s]", toAccount));
			}

			// 3: insert transfer describing this transfer
			final Optional<Long> opTransferId = insertTransfer(fromAccount.number, toAccount.number, amount, new Date(),
					conn);
			if (opTransferId.isPresent()) {
				conn.commit();
				return Result.success(opTransferId.get());
			} else {
				conn.rollback();
				return Result.failure("Could not perform a transfer.");
			}
		} catch (final Exception ex) {
			try {
				conn.rollback();
			} catch (final Exception e) {
				e.printStackTrace();
			}
			ex.printStackTrace();
			return Result.failure(ex);
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static Optional<BigDecimal> bankAccountAmount(final Long account, final long accountVersion,
			final Connection conn) {
		try (final PreparedStatement ps = conn
				.prepareStatement("select amount_ from myschema.bankaccount_ where _id = ? and _version = ?")) {
			ps.setLong(1, account);
			ps.setLong(2, accountVersion);
			try (final ResultSet rs = ps.executeQuery()) {
				return rs.next() ? of(rs.getBigDecimal("amount_")) : empty();
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
			return empty();
		}
	}

	public static Optional<BankAccount> bankAccountById(final Long account, final Connection conn) {
		try (final PreparedStatement ps = conn.prepareStatement("select * from myschema.bankaccount_ where _id = ?")) {
			ps.setLong(1, account);
			try (final ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					final var b = new BankAccount(rs.getLong("_id"), rs.getString("number_"), rs.getLong("owner_"),
							rs.getBigDecimal("amount_"), rs.getLong("_version"));
					return Optional.of(b);
				}
			}
		} catch (final Exception ex) {
			ex.printStackTrace();

		}
		return Optional.empty();
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println("Example runner. \n");

		final String dbUrl = "jdbc:postgresql://localhost:5432/foundation";

		final Optional<BankAccount> account1;
		final Optional<BankAccount> account2;
		try (final Connection conn = DriverManager.getConnection(dbUrl, "dbuser", "dbpassw0rd")) {
			conn.setAutoCommit(true);
			System.out.printf("The number of personnel records deleted: %s%n", deleteAllPersonnel(conn));
			account1 = mkDate(1906, 12, 9).flatMap(dob -> insertPerson("Grace", "Hopper", dob, conn))
					.flatMap(owner -> insertBankAccount("10000300", owner, new BigDecimal("25.00"), conn))
					.flatMap(id -> bankAccountById(id, conn));
			account2 = mkDate(1939, 12, 7).flatMap(dob -> insertPerson("Barbara", "Liskov", dob, conn))
					.flatMap(owner -> insertBankAccount("10000301", owner, new BigDecimal("25.00"), conn))
					.flatMap(id -> bankAccountById(id, conn));

			allPersons(conn).forEach(System.out::println);

			System.out.println("\nCreated bank accounts: ");
			allBankAccount(conn).forEach(System.out::println);
		}

		try (final Connection conn = DriverManager.getConnection(dbUrl, "dbuser", "dbpassw0rd")) {
			System.out.println("\nTransfering money between accounts...");
			if (account1.isPresent() && account2.isPresent()) {
				transfer(account1.get(), account2.get(), new BigDecimal("10.00"), conn).ifPresent(System.out::println);
			}

			System.out.println("\nBank accounts after transfer:");
			allBankAccount(conn).forEach(System.out::println);
		}

		try (final Connection conn = DriverManager.getConnection(dbUrl, "dbuser", "dbpassw0rd")) {
			System.out.println("\nTransfering money between accounts...");
			if (account1.isPresent() && account2.isPresent()) {
				transfer(account1.get(), account2.get(), new BigDecimal("30.00"), conn).ifPresent(System.out::println);
			}

			System.out.println("\nBank accounts after transfer:");
			allBankAccount(conn).forEach(System.out::println);
		}
	}
}
