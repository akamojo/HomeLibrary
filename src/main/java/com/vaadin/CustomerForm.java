package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class CustomerForm extends FormLayout {
	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");
	private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
	private Button save = new Button("Save");
	private Button delete = new Button("Delete");
	private CustomerService service = CustomerService.getInstance();
	private Customer customer;
	private LibraryView view;
	private Binder<Customer> binder = new Binder<>(Customer.class);

	public CustomerForm(LibraryView libraryView) {
	    this.view = libraryView;
	    status.setItems(CustomerStatus.values());
	    binder.bindInstanceFields(this);
	    save.getElement().setAttribute("theme", "primary");
	    save.addClickListener(e -> this.save());
	    delete.addClickListener(e -> this.delete());
	    HorizontalLayout buttons = new HorizontalLayout(save, delete);
	    

	    add(firstName, lastName, status, buttons);

	    setCustomer(null);
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
		binder.setBean(customer);
	    boolean enabled = customer != null;
	    save.setEnabled(enabled);
	    delete.setEnabled(enabled);
	    if (enabled) {
	        firstName.focus();
	    }
	}
	
	private void delete() {
	    service.delete(customer);
	    view.updateList();
	    setCustomer(null);
	}

	private void save() {
	    service.save(customer);
	    view.updateList();
	    setCustomer(null);
	}
}
