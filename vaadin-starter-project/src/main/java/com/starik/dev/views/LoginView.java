package com.starik.dev.views;

import com.starik.dev.domain.User;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route("login")
@HtmlImport("frontend://styles/shared-styles.html")
@PageTitle("Test App Login")
public class LoginView extends VerticalLayout 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Label lblError = new Label();
	
	public LoginView() 
	{
		setSizeFull();
		setMargin(false);
		setSpacing(false);
		setClassName("loginview");

		VerticalLayout formContainer = new VerticalLayout();
		formContainer.setSizeUndefined();
		formContainer.setClassName("login-panel");
		
		H3 login = new H3("Login");
		lblError.setVisible(false);
		lblError.getStyle().set("color", "red");
		
		Component loginForm = buildLoginForm();
		formContainer.add(login, lblError, loginForm);
		
		add(formContainer);
		setJustifyContentMode(JustifyContentMode.CENTER);
		setHorizontalComponentAlignment(Alignment.CENTER, formContainer);

		Notification notification = new Notification("Username = admin, Password = admin");
		notification.getElement().setAttribute("theme", "error");
		notification.setPosition(Position.BOTTOM_CENTER);
		notification.setDuration(10000);
		notification.open();
	}
	
	private Component buildLoginForm() 
	{
		FormLayout fields = new FormLayout();

		final TextField username = new TextField("UserName");
		username.setPrefixComponent(VaadinIcon.USER.create());
		username.setRequired(true);
		
		username.addValueChangeListener(event -> 
		{
			if(!event.getValue().trim().isEmpty())
			{
				lblError.setVisible(false);
			}
		});
		
		final PasswordField password = new PasswordField("Password");
		password.setPrefixComponent(VaadinIcon.LOCK.create());
		password.setRequired(true);
		
		password.addValueChangeListener(event -> 
		{
			if(!event.getValue().trim().isEmpty())
			{
				lblError.setVisible(false);
			}
		});

		final Button signin = new Button("Sign In");
		signin.getElement().setAttribute("theme", "primary");

		fields.add(username, password, signin);
				
		signin.addClickListener(event ->
		{
			if(!username.isEmpty() && !password.isEmpty())
			{
				if(username.getValue().equals("admin") && password.getValue().equals("admin"))
				{
					User user = new User();
					user.setRole("admin");
					user.setFirstName(username.getValue());
					VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
					lblError.setVisible(false);
					UI.getCurrent().navigate("home");
				}
				else
				{
					lblError.setVisible(true);
					lblError.setText("Invalid Username or Password");
					username.focus();
				}
			}
			else
			{
				lblError.setText("");
				lblError.setVisible(true);
				if(username.isEmpty())
				{
					lblError.setText("Please Enter Username");
					username.focus();
				}
				else
				{
					lblError.setText("Please Enter Password");
					password.focus();
				}
			}
		});
		return fields;
	}
}