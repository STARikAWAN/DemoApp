package com.starik.dev;

import com.starik.dev.domain.User;
import com.starik.dev.views.LoginView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "")
public class DemoHome extends VerticalLayout implements BeforeEnterObserver
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DemoHome()  
	{
		setSizeFull();
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) 
	{
		User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
		if (user != null && "admin".equals(user.getRole())) 
		{
			// Authenticated user
			event.rerouteTo(MainView.class);
		}
		else 
		{
			event.rerouteTo(LoginView.class);
		}
	}
}