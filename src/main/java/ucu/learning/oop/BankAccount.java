package ucu.learning.oop;

import static java.lang.String.format;

import java.math.BigDecimal;

public class BankAccount {
	public final Long id;
	public final String number;
	public final Person owner; // id of person
	public final BigDecimal amount;
	public final Long version;

	public BankAccount(final Long id, final String number, final Person owner, final BigDecimal amount, final Long version) {
		this.id = id;
		this.number = number;
		this.owner = owner;
		this.amount = amount;
		this.version = version;
	}

	@Override
	public String toString() {
		return format("BankAccount (%s, %s, %s, %s, %s)", id, number, owner, amount, version);
	}
}