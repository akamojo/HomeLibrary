package com.vaadin;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class AccountForm extends FormLayout {
	private Label label = new Label("Welcome to Home Library service!");
	private ComboBox<Choice> choice = new ComboBox<>("Choose wisely");
	private TextField name = new TextField("Username");
	private TextField email = new TextField("Email");
	private PasswordField password = new PasswordField("Password");
	private Button ok = new Button("GO");
	private AccountService service = AccountService.getInstance();
	private Account account;
	private MainView view;
	private Binder<Account> binder = new Binder<>(Account.class);
	private Choice currentChoice;
	
	public AccountForm(MainView mainView) {
		this.view = mainView;
		binder.bindInstanceFields(this);
		password.setRevealButtonVisible(false);
		ok.getElement().setAttribute("theme", "primary");
		ok.addClickListener(e -> this.ok());
		choice.setItems(Choice.values());
		choice.addValueChangeListener(e -> {
			currentChoice = e.getValue();
		});
		
	    VerticalLayout layout = new VerticalLayout(label, choice, name, email, password, ok);
		add(layout);

	    setAccount(null);
	}
	
	public void setAccount(Account account) {
		this.account = account;
		binder.setBean(account);
		boolean enabled = account != null;
		ok.setEnabled(enabled);
		if(enabled) {
			name.focus();
		}
	}
	
	public void ok() {
		Account acc = service.findByName(account.getName());
		if(currentChoice == Choice.LogIn) {
			if(acc != null) {
				if(acc.getPassword().equals(account.getPassword())) {
					if(acc.isAuthorised()) {
						if(acc.getOtherAccount() != -1l) {
							view.log(service.getContacts().get(acc.getOtherAccount()), acc.getName());
						} else
							view.log(acc, acc.getName());
						
						setAccount(null);
					} else {
						Dialog dialog = new Dialog();
						VerticalLayout labels = new VerticalLayout(new Label("Click an activation link in the email we sent you."), 
								new Label("If you didn't receive the email please contact with admin."));
						dialog.add(labels);
						dialog.open();
					}
				} else {
					Dialog dialog = new Dialog();
					dialog.add(new Label("Incorrect password. Try again."));
					dialog.open();
				}
			} else {
				Dialog dialog = new Dialog();
				dialog.add(new Label("You must have an account first to log in."));
				dialog.open();
			}
		} else {
			if(acc != null) {
				Dialog dialog = new Dialog();
				dialog.add(new Label("This username is not available, try another one."));
				dialog.open();
			} else {
				service.save(account);
				Dialog dialog = new Dialog();
				dialog.setCloseOnEsc(false);
				dialog.setCloseOnOutsideClick(false);
				
				Button button = new Button("OK");
				button.addClickListener( e-> {
					dialog.close();
					setAccount(new Account());
				});
				
				VerticalLayout layout = new VerticalLayout(new Label("Click an activation link in the mail we sent you."), button);
				layout.setAlignItems(Alignment.CENTER);
				dialog.add(layout);
				dialog.open();
			}
		}
	}
	
	private enum Choice {
		SignIn, LogIn;
	}

}
