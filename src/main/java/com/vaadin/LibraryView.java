package com.vaadin;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class LibraryView extends VerticalLayout {
	private BookService service = BookService.getInstance();
	private Grid<Book> grid = new Grid<>();
	private TextField filterText = new TextField();
	private BookForm form;

	public LibraryView(Account account, String user) {		
		service.setAccount(account);
		this.form = new BookForm(this, user);
		
		filterText.setPlaceholder("Filter by name...");
		filterText.setValueChangeMode(ValueChangeMode.EAGER);
		filterText.addValueChangeListener(e -> updateList());
		Button clearFilterTextBtn = new Button(new Icon(VaadinIcons.CLOSE_CIRCLE));
		clearFilterTextBtn.addClickListener(e -> filterText.clear());

		HorizontalLayout filtering = new HorizontalLayout(filterText, clearFilterTextBtn);
		Button addBookBtn = new Button("Add new book");
		addBookBtn.addClickListener(e -> {
			grid.asSingleSelect().clear();
			form.setBook(new Book());
		});
		
		Button inviteToLibrary = new Button("Invite user to your library");
		inviteToLibrary.addClickListener(e -> invite());
		
		HorizontalLayout toolbar = new HorizontalLayout(inviteToLibrary, filtering, addBookBtn);

		grid.setSizeFull();
		
		grid.addColumn(Book::getAuthor).setHeader("Author").setFlexGrow(0)
	    .setWidth("200px")
	    .setResizable(false);
		grid.addColumn(Book::getTitle).setHeader("Title").setFlexGrow(0)
	    .setWidth("200px")
	    .setResizable(false);
		grid.addColumn(Book::getStatus).setHeader("Status").setFlexGrow(0)
	    .setWidth("100px")
	    .setResizable(false);
		grid.addColumn(Book::getLoanDate).setHeader("Loan Date").setFlexGrow(0)
	    .setWidth("100px")
	    .setResizable(false);
		grid.addColumn(Book::getBackDate).setHeader("Back Date").setFlexGrow(0)
	    .setWidth("100px")
	    .setResizable(false);

		grid.setWidth("500px");
		
		grid.asSingleSelect().addValueChangeListener(event -> {
			form.setBook(event.getValue());
		});
		
		HorizontalLayout main = new HorizontalLayout(grid, form);
		main.setAlignItems(Alignment.START);
		main.setSizeFull();

		add(toolbar, main);
		setHeight("100vh");
		updateList();
	}
	
	public void invite() {
		Dialog dialog = new Dialog();

		dialog.setCloseOnEsc(false);
		dialog.setCloseOnOutsideClick(false);

		Label instructions = new Label("Type an email address of a person you want to invite.");
		TextField mail = new TextField();
		
		Button confirmButton = new Button("Confirm", event -> {
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setSSLOnConnect(true);
			email.setAuthentication("kwiatek.homelibrary@gmail.com", "testowehaslo1");
			
			try {
				email.setFrom("homelibrary@gmail.com");
				email.addTo(mail.getValue());
				email.setSubject("You have an invitation");
				email.setHtmlMsg("<a href=\"http://localhost:8080/invite/" + service.getAccount().getId() + "\">Invitation</a>");
				email.send();
				
			} catch (EmailException e) {
				e.printStackTrace();
			}
		    dialog.close();
		});
		Button cancelButton = new Button("Cancel", event -> {
		    dialog.close();
		});
		
		HorizontalLayout buttons = new HorizontalLayout(confirmButton, cancelButton);
		VerticalLayout main = new VerticalLayout(instructions, mail, buttons);
		main.setAlignItems(Alignment.CENTER);
		dialog.add(main);
		dialog.open();
	}

	public void updateList() {
		grid.setItems(service.findAll(filterText.getValue()));
	}
}
