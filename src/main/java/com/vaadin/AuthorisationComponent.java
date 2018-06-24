package com.vaadin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "auth")
public class AuthorisationComponent extends Div implements HasUrlParameter<String>{

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		if(AccountService.getInstance().getContacts().get(Long.parseLong(parameter)) != null)
			AccountService.getInstance().getContacts().get(Long.parseLong(parameter)).setAuthorised(true);
		
		Label lab = new Label(String.format("Authorised!", parameter));
		NativeButton button = new NativeButton("Log in!");
		button.addClickListener( e-> {
		     button.getUI().ifPresent(ui -> ui.navigate(""));
		});
		VerticalLayout layout = new VerticalLayout(lab, button);
		add(layout);
		
	}
	
}
