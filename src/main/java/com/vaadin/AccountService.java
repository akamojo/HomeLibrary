package com.vaadin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class AccountService {

	private static AccountService instance;
	private static final Logger LOGGER = Logger.getLogger(AccountService.class.getName());

	private final HashMap<Long, Account> contacts = new HashMap<>();
	private long nextId = 0;

	private AccountService() {
	}

	public static AccountService getInstance() {
		if (instance == null) {
			instance = new AccountService();
		}
		return instance;
	}
	
	public void sendEmail(Account account) {
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		email.setSSLOnConnect(true);
		email.setAuthentication("kwiatek.homelibrary@gmail.com", "testowehaslo1");
		
		try {
			email.setFrom("homelibrary@gmail.com");
			email.addTo(account.getEmail());
			email.setSubject("Potwierdz swoje haslo");
			email.setHtmlMsg("<a href=\"http://localhost:8080/auth/" + account.getId() + "\">Authorise account</a>");
			email.send();
			
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public HashMap<Long, Account> getContacts() {
		return contacts;
	}
	
	public Account findByName(String name) {
		for(Entry<Long, Account> e: contacts.entrySet()) {
			if(e.getValue().getName().equals(name)) {
				return e.getValue();
			}
		}
		return null;
	}

	public synchronized long count() {
		return contacts.size();
	}

	public synchronized void delete(Account value) {
		contacts.remove(value.getId());
	}

	public synchronized void save(Account entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Account is null.");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		sendEmail(entry);
		try {
			entry = (Account) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		contacts.put(entry.getId(), entry);
	}

}
