package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class CommentForm extends FormLayout {
	private TextArea text = new TextArea("Comment");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private CommentsView view;
	private Comment comment;
	private Binder<Comment> binder = new Binder<>(Comment.class);
	private CommentService service = CommentService.getInstance();
	
	public CommentForm(CommentsView view) {
		this.view = view;
		binder.bindInstanceFields(this);
	    save.getElement().setAttribute("theme", "primary");
	    save.addClickListener(e -> this.save());
	    delete.addClickListener(e -> this.delete());
	    HorizontalLayout buttons = new HorizontalLayout(save, delete);
	    

	    add(text, buttons);

	    setComment(null);
	}
	
	public void setComment(Comment comment) {
		this.comment = comment;
		binder.setBean(comment);
	    boolean enabled = comment != null;
	    save.setEnabled(enabled);
	    delete.setEnabled(enabled);
	    if (enabled) {
	        text.focus();
	    }
	}
	
	private void delete() {
	    service.delete(comment);
	    view.updateList();
	    setComment(null);
	}

	private void save() {
	    service.save(comment);
	    view.updateList();
	    setComment(null);
	}
	
}
