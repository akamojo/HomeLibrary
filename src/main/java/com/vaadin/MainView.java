package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcons;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

/**
 * The main view contains a button and a template element.
 */
@PageTitle("Home Library")
@BodySize(height = "100vh", width = "100vw")
@HtmlImport("styles/shared-styles.html")
@Route("")
@Theme(Lumo.class)
public class MainView extends VerticalLayout {

	private AccountForm acc = new AccountForm(this);
	private LibraryView lib;
	public static Long otherAccount = -1l;

	public MainView() {
		Account newAccount = new Account();
		if (otherAccount != -1l) {
			newAccount.setOtherAccount(otherAccount);
			otherAccount = -1l;
		}
		acc.setAccount(newAccount);
		add(acc);
		setHeight("100vh");
	}
	
	public void log(Account account, String user) {
		lib = new LibraryView(account, user);
		removeAll();
		add(lib);
	}
}
