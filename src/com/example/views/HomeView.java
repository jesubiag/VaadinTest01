package com.example.views;

import com.example.vaadintest01.Vaadintest01UI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class HomeView extends VerticalLayout implements View {

	private static final long serialVersionUID = 1L;
	Panel panel;

	public HomeView() {
		
		setSizeFull();
        
        // Layout with menu on left and view area on right
        HorizontalLayout hLayout = new HorizontalLayout();
        hLayout.setSizeFull();

        // Have a menu on the left side of the screen
        Panel menu = new Panel("PEI");
        menu.setHeight("100%");
        menu.setWidth(null);
        VerticalLayout menuContent = new VerticalLayout();
        menuContent.addComponent(new Button("Inicio",
                  new ButtonListener("inicio")));
        menuContent.addComponent(new Button("Materias",
                  new ButtonListener("materias")));
        menuContent.addComponent(new Button("Mi Calendario",
                  new ButtonListener("calendario")));
        menuContent.addComponent(new Button("Mi Actividad",
                  new ButtonListener("actividad")));
        menuContent.setWidth(null);
        menuContent.setMargin(true);
        menu.setContent(menuContent);
        hLayout.addComponent(menu);

        // A panel that contains a content area on right
        panel = new Panel("");
        panel.setSizeFull();
        hLayout.addComponent(panel);
        hLayout.setExpandRatio(panel, 1.0f);

        addComponent(hLayout);
        setExpandRatio(hLayout, 1.0f);
        
        // Allow going back to the start
        Button logout = new Button("Logout",
                   new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	getUI().getNavigator().navigateTo(Vaadintest01UI.MAINVIEW);
            }
        });
        addComponent(logout);

	}
	
	class ButtonListener implements Button.ClickListener {

		private static final long serialVersionUID = 1L;
		String menuitem;
        
        public ButtonListener(String menuitem) {
            //this.menuitem = menuitem;
        	this.menuitem = Vaadintest01UI.HOMEVIEW;
        }

        @Override
        public void buttonClick(ClickEvent event) {
            // Navigate to a specific state
            getUI().getNavigator().navigateTo(Vaadintest01UI.MAINVIEW + menuitem);
        }
    }
	
	@Override
	public void enter(ViewChangeEvent event) {
		// Hacer algo
	}

}
