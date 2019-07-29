package com.lenart.spaceflightmanagement.GUI;

import com.vaadin.flow.component.button.Button;

public class MenuButton extends Button {

    private Object locationClass;

    public MenuButton(String text, Object locationClass) {
        this.locationClass = locationClass;
        setText(text);
        addClickListener(e -> this.getUI().ifPresent(ui -> ui.navigate(getLocation())));
        setClassName("menuButton");
    }

    public MenuButton(String text, String location) {
        setText(text);
        addClickListener(e -> this.getUI().ifPresent(ui -> ui.navigate(location)));
        setClassName("menuButton");
    }

    private String getLocation(){
        String line = String.valueOf(locationClass);
        for (int i = line.length() - 1; i > 0 ; i--){
            if(line.charAt(i) == '.')
                return line.substring(i+1).toLowerCase();
        }
        return null;
    }
}
