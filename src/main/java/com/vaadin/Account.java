package com.vaadin;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Account implements Serializable, Cloneable {
	private Long id;
	private String name = "";
	private String email = "";
	private String password = "";
	private boolean authorised = false;
	private HashMap<Long, Book> books = new HashMap<>();
	private Long otherAccount = -1l;
	
	
	public Long getOtherAccount() {
		return otherAccount;
	}
	public void setOtherAccount(Long otherAccount) {
		this.otherAccount = otherAccount;
	}
	public HashMap<Long, Book> getBooks() {
		return books;
	}
	public void setBooks(HashMap<Long, Book> books) {
		this.books = books;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Account && obj.getClass().equals(getClass())) {
			return this.id.equals(((Account) obj).id);
		}

		return false;
	}
	
	@Override
	public String toString() {
		return "Account [name=" + name + ", email=" + email + ", password=" + password + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Override
	public Account clone() throws CloneNotSupportedException {
		return (Account) super.clone();
	}
	public boolean isAuthorised() {
		return authorised;
	}
	public void setAuthorised(boolean authorised) {
		this.authorised = authorised;
	}
	
}
