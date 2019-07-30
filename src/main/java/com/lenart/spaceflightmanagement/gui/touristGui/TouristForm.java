package com.lenart.spaceflightmanagement.gui.touristGui;

import com.lenart.spaceflightmanagement.model.Flight;
import com.lenart.spaceflightmanagement.model.Tourist;
import com.lenart.spaceflightmanagement.model.typeTourist.CountryTouristType;
import com.lenart.spaceflightmanagement.model.typeTourist.GenderTouristType;
import com.lenart.spaceflightmanagement.service.FlightService;
import com.lenart.spaceflightmanagement.service.TouristService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.selection.MultiSelect;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TouristForm extends FormLayout {

    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private ComboBox<GenderTouristType> gender = new ComboBox<>("Gender");
    private ComboBox<CountryTouristType> country = new ComboBox<>("Country");
    private TextField notes = new TextField("Notes");
    private DatePicker birthDate = new DatePicker("Birthdate");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<Tourist> binder = new Binder<>();
    private TouristGui touristGui;
    private TouristService touristService;
    private FlightService flightService;
    private Grid<Flight> flightGrid = new Grid<>();
    private List<Flight> flightList;

    private MultiSelect<Grid<Flight>, Flight> multiSelect;
    private HorizontalLayout buttons;

    public TouristForm(TouristGui touristGui, TouristService touristService, FlightService flightService) {
        this.touristGui = touristGui;
        this.touristService = touristService;
        this.flightService = flightService;

        gender.setItems(GenderTouristType.values());
        country.setItems(CountryTouristType.values());

        flightControlMethod();

        buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(firstName, lastName, gender, country, notes, birthDate, flightGrid, buttons);

        multiSelect = flightGrid.asMultiSelect();

        bindBinderForTouristFormFields();

//        touristGrid.addSelectionListener(listener -> {
//            Set<Tourist> selected = touristGrid.getSelectedItems();
//            Set<Flight> flights = selected.stream().findFirst().get().getFlightList();
//            flightGrid.setItems(flights);
//        });


        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
    }

    private void bindBinderForTouristFormFields() {
        binder.forField(firstName)
                .asRequired("Wrong First Name")
                .bind(Tourist::getFirstName, Tourist::setFirstName);
        binder.forField(lastName)
                .asRequired("Wrong Last Name")
                .bind(Tourist::getLastName, Tourist::setLastName);
        binder.forField(gender)
                .asRequired("Select correct value")
                .bind(Tourist::getGender, Tourist::setGender);
        binder.forField(country)
                .asRequired("Select correct country")
                .bind(Tourist::getCountry, Tourist::setCountry);
        binder.forField(notes)
                .asRequired("Wrong notes")
                .bind(Tourist::getNotes, Tourist::setNotes);
        binder.forField(birthDate)
                .asRequired("Wrong Date Of Birth")
                .bind(Tourist::getDateOfBirth, Tourist::setDateOfBirth);
        binder.forField(multiSelect)
//                .asRequired("Choose At least 1")
                .bind(Tourist::getFlightSet, Tourist::setFlightSet);

//        binder.addValueChangeListener(event -> {
//            Notification message = new Notification();
//            message.setDuration(2000);
//            message.setText("Tourist added to list");
//            message.open();
//            add(message);
//        });

        binder.addStatusChangeListener(status -> {
                    boolean emptyFields = Stream.of(firstName, lastName, gender, country, notes, birthDate)
                            .anyMatch(binding -> binding.isEmpty());
                    save.setEnabled(!status.hasValidationErrors() && !emptyFields);
                }
        );
    }

    private void flightControlMethod() {
        flightList = flightService.findAll();
        flightGrid.setItems(flightList);

        addColumnsFlights();

        flightGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        flightGrid.addSelectionListener(event -> {
            Notification message = new Notification();
            message.setDuration(2000);
            Set<Flight> selected = event.getAllSelectedItems();
            if (selected.size() == 1) {
                message.setText("1 flight selected");
            } else if (selected.size() >= 1) {
                message.setText(selected.size() + " flights selected");
            }
            message.open();
            add(message);
        });
    }

    private void addColumnsFlights() {
        flightGrid.addColumn(Flight::getDepartureDate).setHeader("Departure Date");
        flightGrid.addColumn(Flight::getArrivalDate).setHeader("Arrival Date");
        flightGrid.addColumn(Flight::getCountOfFreeSeats).setHeader("Count Of Seats");
        flightGrid.addColumn(Flight::getCostOfTicket).setHeader("Cost Of Ticket");
    }

    public void setTourist(Tourist tourist) {
        binder.setBean(tourist);

        if (tourist == null) {
            setVisible(false);
            multiSelect.clear();
        } else {
            setVisible(true);
            multiSelect.setValue(tourist.getFlightSet());
            firstName.focus();
        }
    }

    private void save() {
        Tourist tourist = binder.getBean();
        touristService.save(tourist);
        touristGui.updateTouristList();
        setTourist(null);
    }

    private void delete() {
        Tourist tourist = binder.getBean();
        touristService.delete(tourist);
        touristGui.updateTouristList();
        setTourist(null);
    }

    public void update(Tourist tourist){
        tourist.getFlightSet().forEach(flight -> multiSelect.select(flight));
    }
}
