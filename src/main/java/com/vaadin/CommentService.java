package com.vaadin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommentService {
	private static CommentService instance;
	private static final Logger LOGGER = Logger.getLogger(CommentService.class.getName());

	private long nextId = 0;
	private Book book;


	private CommentService() {
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public static CommentService getInstance() {
		if (instance == null) {
			instance = new CommentService();
		}
		return instance;
	}

	
	public synchronized long count() {
		return book.getComments().size();
	}

	public synchronized void delete(Comment value) {
		book.getComments().remove(value.getId());
	}

	public synchronized void save(Comment entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Comment is null.");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Comment) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		book.getComments().put(entry.getId(), entry);
	}
}
