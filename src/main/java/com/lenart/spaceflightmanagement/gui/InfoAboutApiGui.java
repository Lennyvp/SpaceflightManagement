package com.lenart.spaceflightmanagement.gui;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
@StyleSheet("style.css")
public class InfoAboutApiGui extends VerticalLayout {

    public InfoAboutApiGui() {
        String[] flightControllerDescriptionTab = splitDescription(getFlightControllerDescription());
        String[] touristControllerDescriptionTab = splitDescription(getTouristControllerDescription());

        Div flightDiv = printDescriptionDiv(flightControllerDescriptionTab);
        Div touristDiv = printDescriptionDiv(touristControllerDescriptionTab);

        add(flightDiv, touristDiv);
    }

    private Div printDescriptionDiv(String[] descriptionTab){
        Div div = new Div();
        for (String tabLine : descriptionTab) {
            add(new Paragraph(tabLine));
        }
        return div;
    }

    private String[] splitDescription(String description){
        return description.split("\n");
    }

    private String getFlightControllerDescription() {
        return "Flight:\n" +
                "/api/flights - GET, POST\n" +
                "/api/flights/{flight_id} - GET, PUT, DEL\n" +
                "/api/flights/{flight_id}/addtourists/{tourist_id} - GET\n" +
                "/api/flights/{flight_id}/deltourists/{tourist_id} - DEL\n";
    }

    private String getTouristControllerDescription() {
        return "Tourist:\n" +
                "/api/tourists - GET, POST\n" +
                "/api/tourists/{tourist_id} - GET, PUT, DEL\n" +
                "/api/tourists/{tourist_id}/addtourists/{flight_id} - GET\n" +
                "/api/tourists/{tourist_id}/deltourists/{flight_id} - DEL\n";
    }

}
