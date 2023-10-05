package ucu.learning.oop;

import static java.lang.String.format;

import java.math.BigDecimal;
import java.util.Date;

public class Transfer {
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