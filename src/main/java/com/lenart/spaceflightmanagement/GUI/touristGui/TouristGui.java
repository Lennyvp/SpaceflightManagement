package com.lenart.spaceflightmanagement.GUI.touristGui;

import com.lenart.spaceflightmanagement.model.Tourist;
import com.lenart.spaceflightmanagement.service.FlightService;
import com.lenart.spaceflightmanagement.service.TouristService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

//("tourists")
@Route
public class TouristGui extends VerticalLayout {

    private TouristService touristService;

    private TextField filterText = new TextField();
    private Grid<Tourist> touristGrid = new Grid<>();
    private List<Tourist> tourists;

    private TouristForm touristForm;

    @Autowired
    public TouristGui(TouristService touristService, FlightService flightService) {
        this.touristService = touristService;

        touristForm = new TouristForm(this, touristService, flightService);

        tourists = touristService.findAllTourists();

        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button addTouristBtn = new Button("Add new tourist");
        addTouristBtn.addClickListener(e -> {
            touristGrid.asSingleSelect().clear();
            touristForm.setTourist(new Tourist());
        });

        touristGrid.setItems(tourists);

        touristGrid.asSingleSelect().addValueChangeListener(event ->
                touristForm.setTourist(touristGrid.asSingleSelect().getValue()));

        addAllTouristColumns();

        HorizontalLayout toolbar = new HorizontalLayout(filterText,
                addTouristBtn);
        HorizontalLayout mainContent = new HorizontalLayout(touristGrid,
                touristForm);

        mainContent.setSizeFull();
        touristGrid.setSizeFull();
        setSizeFull();

        add(toolbar, mainContent);

        updateList();

        touristForm.setTourist(null);
    }

    private void addAllTouristColumns(){
        touristGrid.addColumn(Tourist::getFirstName).setHeader("First Name");
        touristGrid.addColumn(Tourist::getLastName).setHeader("Last Name");
        touristGrid.addColumn(Tourist::getGender).setHeader("Gender");
        touristGrid.addColumn(Tourist::getCountry).setHeader("Country");
        touristGrid.addColumn(Tourist::getNotes).setHeader("Notes");
        touristGrid.addColumn(Tourist::getDateOfBirth).setHeader("Date Of Birth");

    }

    void updateList() {
        if(filterText.getValue().equals("")){
            touristGrid.setItems(tourists);
        } else {
            touristGrid.setItems(touristService.findAllTouristsByName(filterText.getValue()));
        }
    }
}