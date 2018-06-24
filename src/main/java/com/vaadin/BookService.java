package com.vaadin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BookService {
	private static BookService instance;
	private static final Logger LOGGER = Logger.getLogger(BookService.class.getName());

	private final HashMap<Long, Book> books = new HashMap<>();
	private long nextId = 0;
	private Account account;

	private BookService() {
	}

	/**
	 * @return a reference to an example facade for Customer objects.
	 */
	public static BookService getInstance() {
		if (instance == null) {
			instance = new BookService();
		}
		return instance;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public HashMap<Long, Book> getBooks() {
		return books;
	}


	/**
	 * @return the amount of all customers in the system
	 */
	public synchronized long count() {
		return account.getBooks().size();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param value
	 *            the Customer to be deleted
	 */
	public synchronized void delete(Book value) {
		account.getBooks().remove(value.getId());
	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier
	 * for new Customer instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Book entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Book is null.");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Book) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		account.getBooks().put(entry.getId(), entry);
	}
	
	public synchronized List<Book> findAll() {
		return findAll(null);
	}

	/**
	 * Finds all Customer's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Customer objects
	 */
	public synchronized List<Book> findAll(String stringFilter) {
		ArrayList<Book> arrayList = new ArrayList<>();
		for (Book contact : account.getBooks().values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Book>() {

			@Override
			public int compare(Book o1, Book o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		return arrayList;
	}
	
	public Book findByTitleAndAuthor(String title, String author) {
		for(Entry<Long, Book> e: account.getBooks().entrySet()) {
			if(e.getValue().getTitle().equals(title) && e.getValue().getAuthor().equals(author)) {
				return e.getValue();
			}
		}
		return null;
	}


	public synchronized List<Book> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Book> arrayList = new ArrayList<>();
		for (Book contact : account.getBooks().values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(BookService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Book>() {

			@Override
			public int compare(Book o1, Book o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}


}
