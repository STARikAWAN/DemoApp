package com.starik.dev;

import com.starik.dev.data.CustomerService;
import com.starik.dev.domain.Customer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
@Route(value = "home", layout = MainAppLayout.class)
@PageTitle("Home")
public class MainView extends VerticalLayout {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CustomerService service = CustomerService.getInstance();
	private Grid<Customer> grid = new Grid<>(Customer.class);
	
	private TextField filterText = new TextField();
	//private CustomerForm form = new CustomerForm(this);

    public MainView() 
    {
    	filterText.setPlaceholder("Filter By Name..");
    	filterText.setValueChangeMode(ValueChangeMode.EAGER);
    	filterText.addValueChangeListener(e -> updateList());
    	
    	Button clearText = new Button(VaadinIcon.CLOSE_CIRCLE.create());
    	clearText.setClassName("borderless");
    	clearText.addClickListener(e -> filterText.clear());
    	
    	filterText.setSuffixComponent(clearText);
    	
    	HorizontalLayout filtering = new HorizontalLayout(filterText);
    	
    	// limit and define the order of properties shown by Grid
    	grid.setColumns("firstName", "lastName", "status");

    	HorizontalLayout main = new HorizontalLayout(grid/*, form*/);
    	main.setSizeFull();
    	grid.setSizeFull();

    	add(filtering, main);


/*    	Button addCustomerBtn = new Button("Add new customer");
    	addCustomerBtn.addClickListener(e -> 
    	{
    	    grid.asSingleSelect().clear();
    	    form.setCustomer(new Customer());
    	});
    	grid.asSingleSelect().addValueChangeListener(event -> 
    	{
    		form.setCustomer(event.getValue());
    	});*/

    	// make layout use full height (and grid expand to consume it)
    	setSizeFull();

    	updateList();
    	
    }
    
    public void updateList() 
    {
    	grid.setItems(service.findAll(filterText.getValue()));
    }
}
