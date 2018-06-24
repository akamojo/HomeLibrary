package com.vaadin;

import java.util.Date;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class BookForm extends FormLayout {
	private TextField author = new TextField("Author");
	private TextField title = new TextField("Title");
	private ComboBox<BookStatus> status = new ComboBox<>("Status");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private BookService service = BookService.getInstance();
	private Book book;
	private LibraryView view;
	private Binder<Book> binder = new Binder<>(Book.class);
	private CommentsView comments = new CommentsView();
	private boolean wasOnLoan = false;

	public BookForm(LibraryView libraryView) {
	    this.view = libraryView;
	    	    
	    status.setItems(BookStatus.values());
	    binder.bindInstanceFields(this);
	    save.getElement().setAttribute("theme", "primary");
	    save.addClickListener(e -> this.save());
	    delete.addClickListener(e -> this.delete());
	    HorizontalLayout buttons = new HorizontalLayout(save, delete);
	    

	    add(author, title, status, buttons);

	    setBook(null);
	}
	
	public void setBook(Book book) {
		this.book = book;
		binder.setBean(book);
	    boolean enabled = book != null;
	    save.setEnabled(enabled);
	    delete.setEnabled(enabled);
	    
	    if (enabled) {
	        author.focus();
	        comments.setBook(book);
		    add(comments);
	    }
	}
	
	private void delete() {
	    service.delete(book);
	    view.updateList();
	    setBook(null);
	}

	private void save() {
//		if(service.findByTitleAndAuthor(book.getTitle(), book.getAuthor()) == null) {
			if(book.getStatus() == BookStatus.OnLoan && !wasOnLoan) {
				wasOnLoan = true;
				book.setLoanDate(new Date());
			} else if (book.getStatus() == BookStatus.Available && wasOnLoan) {
				wasOnLoan = false;
				book.setBackDate(new Date());
			}
		    service.save(book);
		    view.updateList();
		    setBook(null);
//		} else {
//			Dialog dialog = new Dialog();
//			dialog.add(new Label("That book already exists in your library."));
//			dialog.open();
//			setBook(null);
//		}
	}
}
