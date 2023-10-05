package ucu.learning.oop;

import static java.lang.String.format;

import java.util.Date;

public class Person {
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
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Person)) {
			return false;
		}

		final Person that = (Person) obj;

		final boolean surnameEq = this.surname != null ? this.surname.equals(that.surname) : that.surname == null;
		final boolean nameEq = this.name != null ? this.name.equals(that.name) : that.name == null;
		return surnameEq && nameEq;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result = 31 * result + (this.surname != null ? this.surname.hashCode() : 0);
		result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return format("Person (%s, %s, %s, %s)", id, surname, name, dob);
	}
}