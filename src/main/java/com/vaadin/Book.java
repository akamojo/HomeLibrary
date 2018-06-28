package com.vaadin;

import java.awt.List;
import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Book implements Serializable, Cloneable {
	private Long id;
	private String author = "";
	private String title = "";
	private BookStatus status;
	private HashMap<Long, Comment> comments = new HashMap<>();
	private Date loanDate = null;
	private Date backDate = null;
	
	public String getLoanDate() {
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		if (loanDate == null)
			return "";
		return df.format(loanDate);
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public String getBackDate() {
		SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		if (backDate == null)
			return "";
		return df.format(backDate);
	}

	public void setBackDate(Date backDate) {
		this.backDate = backDate;
	}

	public ArrayList<Comment> findAll() {
		ArrayList<Comment> pom = new ArrayList<>();
		for(Comment c : comments.values()) {
			try {
				pom.add(c.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return pom;
	}
	
	public HashMap<Long, Comment> getComments() {
		return comments;
	}

	public void setComments(HashMap<Long, Comment> comments) {
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BookStatus getStatus() {
		return status;
	}

	public void setStatus(BookStatus status) {
		this.status = status;
	}

	public boolean isPersisted() {
		return id != null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof Book && obj.getClass().equals(getClass())) {
			return this.id.equals(((Book) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (id == null ? 0 : id.hashCode());
		return hash;
	}

	@Override
	public Book clone() throws CloneNotSupportedException {
		return (Book) super.clone();
	}

	@Override
	public String toString() {
		return author + " " + title;
	}
	
	
}
