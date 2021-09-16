package com.tempo2.application.views.recordtimetrack;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.tempo2.application.views.MainLayout;

@PageTitle("Record Time Track")
@Route(value = "record-time-track", layout = MainLayout.class)
public class RecordTimeTrackView extends HorizontalLayout {

    private TextField name;
    private Button sayHello;

    public RecordTimeTrackView() {
        addClassName("record-time-track-view");
        name = new TextField("Your name");
        sayHello = new Button("Say hello");
        add(name, sayHello);
        setVerticalComponentAlignment(Alignment.END, name, sayHello);
        sayHello.addClickListener(e -> {
            Notification.show("Hello " + name.getValue());
        });
    }

}
