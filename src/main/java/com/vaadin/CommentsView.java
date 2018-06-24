package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

public class CommentsView extends VerticalLayout{
	private Grid<Comment> grid = new Grid<>();
	private CommentForm form;
	private CommentService service = CommentService.getInstance();
	private Book book;
	
	public CommentsView() {
		form = new CommentForm(this);
		
		Button addCommentBtn = new Button("Add new comment");
		addCommentBtn.addClickListener(e -> {
			grid.asSingleSelect().clear();
			form.setComment(new Comment());
		});

		grid.setSizeFull();
		
		grid.addColumn(Comment::getText).setHeader("Text");		
		grid.asSingleSelect().addValueChangeListener(event -> {
			form.setComment(event.getValue());
		});
		
		VerticalLayout main = new VerticalLayout(addCommentBtn, form, grid);
		main.setAlignItems(Alignment.START);
		main.setSizeFull();
		add(main);
		setHeight("50vh");
	}
	
	public void updateList() {
		grid.setItems(service.getBook().findAll());
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
		service.setBook(book);
		updateList();
	}
	
}
