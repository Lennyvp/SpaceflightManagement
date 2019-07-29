package com.lenart.spaceflightmanagement.GUI.flightGui;

import com.lenart.spaceflightmanagement.model.Flight;
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

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

@Route
public class FlightGui extends VerticalLayout {

    private TouristService touristService;
    private FlightService flightService;

    private TextField filterText = new TextField();
    private Grid<Flight> flightGrid = new Grid<>();
    private List<Flight> flights;

    private FlightForm flightForm;

    @Autowired
    public FlightGui(TouristService touristService, FlightService flightService) {
        this.touristService = touristService;
        this.flightService = flightService;

        flightForm = new FlightForm(this, touristService, flightService);

        flights = flightService.findAllFlights();

        filterText.setPlaceholder("Filter...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button addFlightBtn = new Button("Add new flight");
        addFlightBtn.addClickListener(e -> {
            flightGrid.asSingleSelect().clear();
            flightForm.setFlight(new Flight());
        });

        flightGrid.setItems(flights);

        flightGrid.addColumn(Flight::getDepartureDate).setHeader("Departure Date");
        flightGrid.addColumn(Flight::getArrivalDate).setHeader("Arrival Date");
        flightGrid.addColumn(Flight::getCountOfSeats).setHeader("Count Of Seats");
        flightGrid.addColumn(Flight::getCostOfTicket).setHeader("Cost Of Ticket");

        HorizontalLayout toolbar = new HorizontalLayout(filterText,
                addFlightBtn);

        HorizontalLayout mainContent = new HorizontalLayout(flightGrid, flightForm);
        mainContent.setSizeFull();
        flightGrid.setSizeFull();

        add(toolbar, mainContent);

        setSizeFull();

        updateList();

        flightForm.setFlight(null);

        flightGrid.asSingleSelect().addValueChangeListener(event ->
                flightForm.setFlight(flightGrid.asSingleSelect().getValue()));
    }

    public void updateList() {
        if(filterText.getValue().equals("")){
            flightGrid.setItems(flights);
        } else{
//            flightGrid.setItems(flightService.findAllByIdLike(parseLong(filterText.getValue())));
            flightGrid.setItems(flightService.findAllByIntLike(parseInt(filterText.getValue())));
            flightGrid.setItems(flightService.findAllByDateLike(LocalDateTime.parse(filterText.getValue())));
        }
    }
}
