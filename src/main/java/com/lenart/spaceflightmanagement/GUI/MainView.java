package com.lenart.spaceflightmanagement.GUI;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@StyleSheet("style.css")
public class MainView extends VerticalLayout {

    @Autowired
    public MainView() {

        Button buttonToFlight = new Button("Flight Management");
        Button buttonToTourist = new Button("Tourist Management");

        buttonToFlight.addClickListener( e-> {
            buttonToFlight.getUI().ifPresent(ui -> ui.navigate("flightgui"));
        });

        buttonToTourist.addClickListener( e-> {
            buttonToTourist.getUI().ifPresent(ui -> ui.navigate("touristgui"));
        });

        setAlignItems(Alignment.CENTER);

        Label labelMenu = new Label("Menu");
        labelMenu.setWidth("200px");

        buttonToFlight.setWidth("200px");
        buttonToTourist.setWidth("200px");

        VerticalLayout innerVerticalLayoutForMenuDivBorder = new VerticalLayout(labelMenu, buttonToFlight, buttonToTourist);
        innerVerticalLayoutForMenuDivBorder.setClassName("innerVerticalLayoutForMenuDivBorder");

        Div menuDivBorder = new Div(innerVerticalLayoutForMenuDivBorder);
        menuDivBorder.setClassName("menuDivBorder");

        add(menuDivBorder);
    }
}