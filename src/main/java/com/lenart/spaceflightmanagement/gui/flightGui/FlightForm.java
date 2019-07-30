package com.lenart.spaceflightmanagement.gui.flightGui;

import com.lenart.spaceflightmanagement.model.Flight;
import com.lenart.spaceflightmanagement.model.Tourist;
import com.lenart.spaceflightmanagement.service.FlightService;
import com.lenart.spaceflightmanagement.service.TouristService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.selection.MultiSelect;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class FlightForm extends FormLayout {
    private DatePicker arrivalDate = new DatePicker("Arrival Date");
    private TimePicker arrivalTime = new TimePicker("Arrival Time");
    private DatePicker departureDate = new DatePicker("Departure Date");
    private TimePicker departureTime = new TimePicker("Departure Time");
    private NumberField countOfSeats = new NumberField("Count Of Seats");
    private NumberField costOfTickets = new NumberField("Cost Of Tickets");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<Flight> binder = new Binder<>();
    private FlightGui flightGui;
    private TouristService touristService;
    private FlightService flightService;
    private Grid<Tourist> touristGrid = new Grid<>();
    private List<Tourist> touristList;

    private MultiSelect<Grid<Tourist>, Tourist> multiSelect;
    private HorizontalLayout buttons;

    public FlightForm(FlightGui flightGui, TouristService touristService, FlightService flightService) {
        this.flightGui = flightGui;
        this.touristService = touristService;
        this.flightService = flightService;

        touristControllMethod();

        buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(arrivalDate, arrivalTime, departureDate, departureTime, countOfSeats, costOfTickets, touristGrid, buttons);

        multiSelect = touristGrid.asMultiSelect();

        bindBinderForFields();

        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
    }

    private void bindBinderForFields() {
        binder.forField(arrivalDate)
                .asRequired("Wrong Arrival Date")
                .bind(Flight::getArrivalDateWithoutTime, Flight::setArrivalDateWithoutTime);
        binder.forField(arrivalTime)
                .asRequired("Wrong Arrival Time")
                .bind(Flight::getArrivalTime, Flight::setArrivalTime);

        binder.forField(departureDate)
                .asRequired("Wrong Departure Date")
                .bind(Flight::getDepartureDateWithoutTime, Flight::setDepartureDateWithoutTime);
        binder.forField(departureTime)
                .asRequired("Wrong Departure Time")
                .bind(Flight::getDepartureTime, Flight::setDepartureTime);

        binder.forField(countOfSeats)
                .asRequired("Wrong Count Of Seats")
                .bind(flight -> (double) flight.getCountOfFreeSeats(),
                        (flight, Double) -> flight.setCountOfFreeSeats(countOfSeats.getValue().intValue())
                );
        binder.forField(costOfTickets)
                .asRequired("Wrong Cost Of Tickets")
                .bind(Flight::getCostOfTicket, Flight::setCostOfTicket);
        binder.forField(multiSelect)
                .bind(Flight::getTouristSet, Flight::setTouristSet);

        validationForm();
        //        binder.addValueChangeListener(event -> {
//            Notification message = new Notification();
//            message.setDuration(2000);
//            message.setText("Tourist added to list");
//            message.open();
//            add(message);
//        });
    }

    private void touristControllMethod() {
        touristList = touristService.findAll();
        touristGrid.setItems(touristList);

        addColumnsTourists();

        touristGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        touristGrid.addSelectionListener(event -> {
            Notification message = new Notification();
            message.setDuration(2000);
            Set<Tourist> selected = event.getAllSelectedItems();
            if (selected.size() == 1) {
                message.setText("1 tourist selected");
            } else if (selected.size() >= 1) {
                message.setText(selected.size() + " tourists selected");
            }
            message.open();
            add(message);
        });
    }

    private void validationForm() {
        binder.addStatusChangeListener(status -> {
                    boolean emptyFields = Stream.of(arrivalDate,
                            arrivalTime,
                            departureDate,
                            departureTime,
                            countOfSeats,
                            costOfTickets)
                            .anyMatch(binding -> binding.isEmpty());
                    save.setEnabled(!status.hasValidationErrors() && !emptyFields);
                }
        );
    }

    private void addColumnsTourists() {
        touristGrid.addColumn(Tourist::getFirstName).setHeader("First Name");
        touristGrid.addColumn(Tourist::getLastName).setHeader("Last Name");
        touristGrid.addColumn(Tourist::getGender).setHeader("Gender");
        touristGrid.addColumn(Tourist::getCountry).setHeader("Country");
        touristGrid.addColumn(Tourist::getNotes).setHeader("Notes");
        touristGrid.addColumn(Tourist::getDateOfBirth).setHeader("Date Of Birth");
    }

    public void setFlight(Flight flight) {
        binder.setBean(flight);

        if (flight == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

    private void save() {
        Flight flight = binder.getBean();
        flightService.save(flight);
        flightGui.updateFlightList();
        setFlight(null);
    }

    private void delete() {
        Flight flight = binder.getBean();
        flightService.delete(flight);
        flightGui.updateFlightList();
        setFlight(null);
    }
}
