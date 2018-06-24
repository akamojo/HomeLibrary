package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
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
	private BookForm form = new BookForm(this);

	public LibraryView(Account account) {		
		service.setAccount(account);
		
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
		HorizontalLayout toolbar = new HorizontalLayout(filtering, addBookBtn);

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

		grid.setWidth("300px");
		
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

	public void updateList() {
		grid.setItems(service.findAll(filterText.getValue()));
	}
}
