package com.lenart.spaceflightmanagement.GUI;

import com.lenart.spaceflightmanagement.GUI.flightGui.FlightGui;
import com.lenart.spaceflightmanagement.GUI.touristGui.TouristGui;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

@Route
@StyleSheet("style.css")
public class MainView extends VerticalLayout {

    @Autowired
    public MainView() {

        VerticalLayout menuButtonLayout = generateMenuButtons();

        Label labelMenu = new Label("Menu");
        labelMenu.setWidth("200px");

        setAlignItems(Alignment.CENTER);

        VerticalLayout innerVerticalLayoutForMenuDivBorder = new VerticalLayout(labelMenu, menuButtonLayout);
        innerVerticalLayoutForMenuDivBorder.setClassName("innerVerticalLayoutForMenuDivBorder");

        innerVerticalLayoutForMenuDivBorder.setAlignItems(Alignment.CENTER);
        Div menuDivBorder = new Div(innerVerticalLayoutForMenuDivBorder);
        menuDivBorder.setClassName("menuDivBorder");

        add(menuDivBorder);
    }

    private VerticalLayout generateMenuButtons() {
        return new VerticalLayout(
                new MenuButton("Flight Management", FlightGui.class),
                new MenuButton("Tourist Management", TouristGui.class),
                new MenuButton("Info About API", InfoAboutApiGui.class));
    }
}