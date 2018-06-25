package com.vaadin;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

@Route(value = "invite")
public class InvitationComponent extends Div implements HasUrlParameter<String> {
	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		MainView.otherAccount = Long.parseLong(parameter);
		
		Label lab = new Label("Invited!");
		NativeButton button = new NativeButton("Log in! / Sign in!");
		button.addClickListener( e-> {
		     button.getUI().ifPresent(ui -> ui.navigate(""));
		});
		VerticalLayout layout = new VerticalLayout(lab, button);
		add(layout);
		
	}
}
