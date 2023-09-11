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
		return p.getName() == name && p.getSurname() == surname;
	}

	@Override
	public int compareTo(Person p) {
		int result = this.name.compareToIgnoreCase(p.getName());
		if (result != 0) {
			return result;
		}
		return this.surname.compareToIgnoreCase(p.getSurname());
	}
}
