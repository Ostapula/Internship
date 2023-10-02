package ucu.learning;

import static java.lang.String.format;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Function;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;

public class App {

	private static final AtomicLong idGen = new AtomicLong(System.currentTimeMillis() * 100000);

	public static class Person {
		public final Long id;
		public final String surname;
		public final String name;
		public final Date dob;

		public Person(final Long id, final String surname, final String name, final Date dob) {
			this.id = id;
			this.surname = surname;
			this.name = name;
			this.dob = dob;
		}

		@Override
		public String toString() {
			return format("Person (%s, %s, %s, %s)", id, surname, name, dob);
		}
	}

	public static class BankAccount {
		public final Long id;
		public final String number;
		public final Long owner; // id of person
		public final BigDecimal amount;

		public BankAccount(final Long id, final String number, final Long owner, final BigDecimal amount) {
			this.id = id;
			this.number = number;
			this.owner = owner;
			this.amount = amount;
		}

		@Override
		public String toString() {
			return format("BankAccount (%s, %s, %s, %s)", id, number, owner, amount);
		}
	}

	public static class Transfer {
		public final Long id;
		public final Long fromAccount; // id of the from-account
		public final Long toAccount; // id of the to-person
		public final BigDecimal amount;
		public final Date transferDate;

		public Transfer(final Long id, final Long fromAccount, final Long toAccount, final BigDecimal amount,
				final Date transferDate) {
			this.id = id;
			this.fromAccount = fromAccount;
			this.toAccount = toAccount;
			this.amount = amount;
			this.transferDate = transferDate;
		}

		@Override
		public String toString() {
			return format("Transfer (%s, %s, %s, %s, %s)", id, fromAccount, toAccount, amount, transferDate);
		}
	}

	public static Optional<Long> insertPerson(final String surname, final String name, final Date dob,
			final Connection conn) {
		final long id = idGen.incrementAndGet();
		try (final PreparedStatement ps = conn
				.prepareStatement("insert into myschema.person_ (_id, surname_, name_, dob_) values (?, ?, ?, ?)")) {
			ps.setLong(1, id);
			ps.setString(2, surname);
			ps.setString(3, name);
			ps.setDate(4, new java.sql.Date(dob.getTime()));
			if (ps.executeUpdate() != 0) {
				return of(id);
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
			return empty();
		}
		return empty();
	}

	public static Optional<Long> insertBankAccount(final String number, final Long owner, final BigDecimal amount,
			final Connection conn) {
		final long id = idGen.incrementAndGet();
		try (final PreparedStatement ps = conn.prepareStatement(
				"insert into myschema.bankaccount_ (_id, number_, owner_, amount_) values (?, ?, ?, ?)")) {
			ps.setLong(1, id);
			ps.setString(2, number);
			ps.setLong(3, owner);
			ps.setBigDecimal(4, amount);
			if (ps.executeUpdate() != 0) {
				return of(id);
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
			return empty();
		}
		return empty();
	}

	public static Optional<Long> insertTransfer(final Long fromAccount, final Long toAccount, final BigDecimal amount,
			final Date transferDate, final Connection conn) {
		final long id = idGen.incrementAndGet();
		try (final PreparedStatement ps = conn.prepareStatement(
				"insert into myschema.transfer_ (_id, fromaccount_, toaccount_, amount_, transferdate_) values (?, ?, ?, ?, ?)")) {
			ps.setLong(1, id);
			ps.setLong(2, fromAccount);
			ps.setLong(3, toAccount);
			ps.setBigDecimal(4, amount);
			ps.setTimestamp(5, new java.sql.Timestamp(transferDate.getTime()));
			if (ps.executeUpdate() != 0) {
				return of(id);
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
			return empty();
		}
		return empty();
	}

	public static List<Person> allPersons(final Connection conn) {
		final var persons = new ArrayList<Person>();
		try (final Statement st = conn.createStatement();
				final ResultSet rs = st.executeQuery("select * from myschema.person_")) {
			while (rs.next()) {
				var p = new Person(rs.getLong("_id"), rs.getString("surname_"), rs.getString("name_"),
						rs.getDate("dob_"));
				persons.add(p);
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return persons;
	}

	public static List<BankAccount> allBanckAccount(final Connection conn) {
		final var bankAccounts = new ArrayList<BankAccount>();
		try (final Statement st = conn.createStatement();
				final ResultSet rs = st.executeQuery("select * from myschema.bankaccount_")) {
			while (rs.next()) {
				var ba = new BankAccount(rs.getLong("_id"), rs.getString("number_"), rs.getLong("owner_"),
						rs.getBigDecimal("amount_"));
				bankAccounts.add(ba);
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return bankAccounts;
	}

	public static List<Transfer> allTransfers(final Connection conn) {
		final var transfers = new ArrayList<Transfer>();
		try (final Statement st = conn.createStatement();
				final ResultSet rs = st.executeQuery("select * from myschema.transfer_")) {
			while (rs.next()) {
				var tr = new Transfer(rs.getLong("_id"), rs.getLong("fromaccount_"), rs.getLong("toaccount_"),
						rs.getBigDecimal("amount_"), rs.getTimestamp("transferdate_"));
				transfers.add(tr);
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return transfers;
	}

	public static int updateAmountOnAccount(final Long account, final BigDecimal amount, final Connection conn) {
		try (final PreparedStatement ps = conn
				.prepareStatement("update myschema.bankaccount_ set amount_ = ? where _id = ?")) {
			ps.setBigDecimal(1, amount);
			ps.setLong(2, account);
			return ps.executeUpdate();
		} catch (final Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public static Optional<BigDecimal> bankAccountAmount(final Long account, final Connection conn) {
		try (final PreparedStatement ps = conn
				.prepareStatement("select amount_ from myschema.bankaccount_ where _id = ?")) {
			ps.setLong(1, account);
			try (final ResultSet rs = ps.executeQuery()) {
				return rs.next() ? of(rs.getBigDecimal("amount_")) : empty();
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return empty();
	}

	public static class Result<T> {
		private final Optional<T> value;
		private final Optional<Exception> ex;

		private Result(final T value, final Exception ex) {
			this.value = ofNullable(value);
			this.ex = ofNullable(ex);
		}

		public static <V> Result<V> success(final V value) {
			return new Result<V>(value, null);
		}

		public static <V> Result<V> failure(final Exception ex) {
			return new Result<V>(null, ex);
		}

		public static <V> Result<V> failure(final String msgError) {
			return failure(new Exception(msgError));
		}

		public T get() {
			return value.get();
		}

		public Optional<Exception> error() {
			return ex;
		}

		public void ifPresent(final Consumer<? super T> action) {
			value.ifPresent(action);
		}

		public <U> Result<U> map(final Function<? super T, ? extends U> mapper) {
			final Optional<? extends U> mapped = value.map(mapper);
			return (Result<U>) mapped.map(v -> success(v)).orElse(failure("Nothing map over"));
		}

		public <U> Result<U> flatMap(final Function<? super T, Result<U>> mapper) {
			final Optional<Result<U>> r = value.map(v -> mapper.apply(v));
			return r.orElse(failure("Nothing to map over"));
		}
	}

	public static Result<Long> transfer(final Long fromAccount, final Long toAccount, final BigDecimal amount,
			final Connection conn) {
		try {
			if (false == conn.getAutoCommit()) {
				return Result.failure("Cannot be executed in the scope of another transaction.");
			}
			conn.setAutoCommit(false);

			// 1: update fromAccount with fromAccount.amount = fromAccount.amount - amount
			final Optional<BigDecimal> opFromAmount = bankAccountAmount(fromAccount, conn);
			if (opFromAmount.isPresent()) {
				final BigDecimal fromAmount = opFromAmount.get();
				if (1 != updateAmountOnAccount(fromAccount, fromAmount.subtract(amount), conn)) {
					return Result.failure(format("Could not update the amount for account [%s]", fromAccount));
				}
			} else {
				return Result.failure(format("Could not get thr amount for account [%s]", fromAccount));
			}

			// 2: update toAccount with toAccount.amount = toAccount.amount + amount
			final Optional<BigDecimal> opToAmount = bankAccountAmount(toAccount, conn);
			if (opToAmount.isPresent()) {
				final BigDecimal toAmount = opToAmount.get();
				if (1 != updateAmountOnAccount(toAccount, toAmount.add(amount), conn)) {
					return Result.failure(format("Could not update the amount for account [%s]", toAccount));
				}
			} else {
				return Result.failure(format("Could not get the amount for account [%s]", toAccount));
			}

			// 3: insert transfer describing this transfer
			final Optional<Long> opTransferId = insertTransfer(fromAccount, toAccount, amount, new Date(), conn);
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

	public static List<Person> findPersonsBySurname(final String surname, final Connection conn) {
		final var persons = new ArrayList<Person>();
		try (final PreparedStatement ps = conn.prepareStatement("select * from myschema.person_ where surname_ = ?")) {
			ps.setString(1, surname);
			try (final ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					var p = new Person(rs.getLong("_id"), rs.getString("surname_"), rs.getString("name_"),
							rs.getDate("dob_"));
					persons.add(p);
				}
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
		}
		return persons;
	}

	public static int deleteAllPersonnel(final Connection conn) {
		deleteAllBankAccounts(conn);
		try (final PreparedStatement ps = conn.prepareStatement("delete from myschema.person_")) {
			return ps.executeUpdate();
		} catch (final Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public static int deleteAllBankAccounts(final Connection conn) {
		deleteAllTransfers(conn);
		try (final PreparedStatement ps = conn.prepareStatement("delete from myschema.bankaccount_")) {
			return ps.executeUpdate();
		} catch (final Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public static int deleteAllTransfers(final Connection conn) {
		try (final PreparedStatement ps = conn.prepareStatement("delete from myschema.transfer_")) {
			return ps.executeUpdate();
		} catch (final Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

	public static void printId(final Long id) {
		System.out.println("ID: " + id);
	}

	public static Optional<Date> mkDate(final int year, final int month, final int day) {
		try {
			final LocalDate localDate = LocalDate.of(year, month, day);
			return of(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		} catch (final Exception ex) {
			return empty();
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println("Hello World!");

		final String dbUrl = "jdbc:postgresql://localhost:5432/foundation";
		try (final Connection conn = DriverManager.getConnection(dbUrl, "dbuser", "dbpassw0rd")) {
			System.out.printf("The number of personnel records deleted: %s%n", deleteAllPersonnel(conn));
			final Optional<Long> account1 = mkDate(1906, 12, 9)
					.flatMap(dob -> insertPerson("Grace", "Hopper", dob, conn))
					.flatMap(owner -> insertBankAccount("10000300", owner, new BigDecimal("25.00"), conn));
			final Optional<Long> account2 = mkDate(1939, 12, 7)
					.flatMap(dob -> insertPerson("Barbara", "Liskov", dob, conn))
					.flatMap(owner -> insertBankAccount("10000301", owner, new BigDecimal("0.00"), conn));

			findPersonsBySurname("';update myschema.person_ set dob_ = null ;-- -", conn).forEach(System.out::println);
			System.out.println("\nCurrent persons: ");
			allPersons(conn).forEach(System.out::println);

			System.out.println("\nCreated bank accounts: ");
			allBanckAccount(conn).forEach(System.out::println);

			System.out.println("\nPerforming transfer... ");
			transfer(account1.get(), account2.get(), new BigDecimal("10.01"), conn).ifPresent(System.out::println);

			System.out.println("\nBank account after the transfer: ");
			allBanckAccount(conn).forEach(System.out::println);

			System.out.println("\nAll transfers:");
			allTransfers(conn).forEach(System.out::println);
		}
	}
}