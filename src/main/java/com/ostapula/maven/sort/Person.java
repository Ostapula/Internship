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
		if (p.getName() == null || p.getSurname() == null) {
			if (this.name == null)
				return -1;
			return 1;
		} else if (this.name == null || this.surname == null) 
			return -1;
		if (this.equals(p))
			return 0;
		int result = this.name.replaceAll(" ", "").compareToIgnoreCase(p.getName().replaceAll(" ", ""));
		if (result != 0) {
			return result;
		}
		return this.surname.replaceAll(" ", "").compareToIgnoreCase(p.getSurname().replaceAll(" ", ""));
	}
}
