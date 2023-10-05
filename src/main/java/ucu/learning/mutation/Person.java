package ucu.learning.mutation;

import static java.lang.String.format;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Person {
	public Long id;
	public String surname;
	public String name;
	public Date dob;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public static void main(String[] args) {
		var p1 = new Person(0L, "S1", "N1", new Date());
		final Set<Person> persons = new HashSet<>();
		persons.add(p1);
		System.out.println(p1.hashCode());
		System.out.println(persons.contains(p1));
		System.out.println(persons.size());
		p1.setName("N2");
		System.out.println(p1.hashCode());
		persons.add(p1);
		System.out.println(persons.contains(p1));
		System.out.println(persons.size());

	}
}