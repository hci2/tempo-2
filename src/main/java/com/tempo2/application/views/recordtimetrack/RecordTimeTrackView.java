package com.tempo2.application.views.recordtimetrack;

import com.tempo2.application.dto.PersonTimeTrack;
import com.tempo2.application.service.impl.HttpService;
import com.tempo2.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Record Time Track")
@Route(value = "record-time-track", layout = MainLayout.class)
public class RecordTimeTrackView extends HorizontalLayout {

    private EmailField email = new EmailField("Email address");
    private DateTimePicker start = new DateTimePicker("Work Start Time");
    private DateTimePicker end = new DateTimePicker("Work End Time");

    private Button record = new Button("Record");

    private Binder<PersonTimeTrack> binder = new Binder<>(PersonTimeTrack.class);

    public RecordTimeTrackView(HttpService httpService) {
        addClassName("record-time-track-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());
        binder.bindInstanceFields(this);
        clearForm();

        record.addClickListener(e -> {
            httpService.postTimeTrackRecord(binder.getBean());
            Notification.show("Time track for " + email.getValue() + " recorded.", 5000, Notification.Position.MIDDLE);
        });
    }

    private Component createTitle() {
        return new H1("Record Employee Work Time Track");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        email.setErrorMessage("Please enter a valid email address");
        formLayout.add(email, start, end);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        record.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(record);
        return buttonLayout;
    }

    private void clearForm() {
        binder.setBean(new PersonTimeTrack());
    }

}
