package com.ostapula.maven.sort;

public class Person implements Comparable<Person>{

	private final String name;
	private final String surname;

	protected Person(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	@Override
	public String toString() {
		return name + " " + surname;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Person))
			return false;
		Person p = (Person) o;
		return name.equals(p.getName()) && surname.equals(p.getSurname());
	}

	@Override
	public int compareTo(Person p) {
		int result = compare(this.name, p.getName());
		if (result != 0)
			return result;
		return compare(this.surname, p.getSurname());
	}

	private int compare(String string1, String string2) {
		if (string1 == null && string2 == null)
			return 0;
		if (string1 == null)
			return -1;
		if (string2 == null)
			return 1;
		if (string1.equals(string2))
			return 0;
		return string1.replaceAll(" ", "").compareToIgnoreCase(string2.replaceAll(" ", ""));
	}

}
