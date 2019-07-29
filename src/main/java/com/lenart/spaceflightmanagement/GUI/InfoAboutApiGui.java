package com.lenart.spaceflightmanagement.GUI;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Article;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route
public class InfoAboutApiGui extends VerticalLayout {

    public InfoAboutApiGui() {
        Article flightArticle = new Article();
        Article touristArticle = new Article();
//        Text flightArticle = new Text(setFlightControllerDescription());
//        Text touristArticle = new Text(setTouristControllerDescription());

        flightArticle.setText(setFlightControllerDescription());
        touristArticle.setText(setTouristControllerDescription());

        add(flightArticle, touristArticle);
    }

    private String setFlightControllerDescription() {
        return "Flight:\n" +
                "/flights\n" +
                "/flights/add\n" +
                "/flights/del/{flight_id}\n" +
                "/flights/{flight_id}/addtourists/{tourist_id}\n" +
                "/flights/{flight_id}/deltourists/{tourist_id}\n";
    }

    private String setTouristControllerDescription() {
        return "Tourist:\n" +
                "/tourists\n" +
                "/tourists/add\n" +
                "/tourists/del/{tourist_id}\n" +
                "/tourists/{tourist_id}/addflight/{flight_id}\n" +
                "/tourists/{tourist_id}/delflight/{flight_id}\n";
    }

}
