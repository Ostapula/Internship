package ucu.learning;

import static ucu.learning.App.allBanckAccount;
import static ucu.learning.App.deleteAllPersonnel;
import static ucu.learning.App.allPersons;
import static ucu.learning.App.insertBankAccount;
import static ucu.learning.App.insertPerson;
import static ucu.learning.App.mkDate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

//for update", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE - makes the second transaction wait till the first is done

public class PessimisticLocking {

	public static void updateBankAccountWithPessimisticLock(final String accountNumber, final BigDecimal newAmount,
			final Connection conn) {
		try (final PreparedStatement ps = conn.prepareStatement(
				"select * from myschema.bankaccount_ where number_ = ? for update", ResultSet.TYPE_SCROLL_SENSITIVE,
				ResultSet.CONCUR_UPDATABLE)) {
			ps.setString(1, accountNumber);
			try (final ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					rs.updateBigDecimal("amount_", newAmount);
					rs.updateRow();
				}
			}
		} catch (final Exception ex) {
			ex.printStackTrace();
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

			allPersons(conn).forEach(System.out::println);

			System.out.println("\nCreated bank accounts: ");
			allBanckAccount(conn).forEach(System.out::println);

			conn.setAutoCommit(false);
			updateBankAccountWithPessimisticLock("10000300", new BigDecimal("50.00"), conn);
			conn.commit();

			System.out.println("\nBank account after update");
			allBanckAccount(conn).forEach(System.out::println);
		}
	}
}
