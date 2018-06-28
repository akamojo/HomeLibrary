package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "auth")
@PageTitle("HL Authorisation")
public class AuthorisationComponent extends Div implements HasUrlParameter<String> {

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		Label lab;
		if(AccountService.getInstance().getContacts().get(Long.parseLong(parameter)) != null) {
			AccountService.getInstance().getContacts().get(Long.parseLong(parameter)).setAuthorised(true);
			lab = new Label("Authorised!");
		} else {
			lab = new Label("No such account!");
		}
		
		NativeButton button = new NativeButton("Log in!");
		button.addClickListener( e-> {
		     button.getUI().ifPresent(ui -> ui.navigate(""));
		});
		VerticalLayout layout = new VerticalLayout(lab, button);
		add(layout);
		
	}
	
}
