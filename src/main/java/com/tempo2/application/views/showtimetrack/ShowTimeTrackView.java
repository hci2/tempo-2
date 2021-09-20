package com.tempo2.application.views.showtimetrack;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.tempo2.application.dto.PersonTimeTrack;
import com.tempo2.application.service.impl.HttpService;
import com.tempo2.application.util.DateTimeParserUtil;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.tempo2.application.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("Show Time Track")
@Route(value = "show-time-track", layout = MainLayout.class)
public class ShowTimeTrackView extends Div implements AfterNavigationObserver {

    private Grid<PersonTimeTrack> grid = new Grid<>();

    private EmailField email = new EmailField("Email address");
    private IntegerField length = new IntegerField("Amount of rows");
    private Button search = new Button("Search");

    @Autowired
    private HttpService httpService;

    public ShowTimeTrackView(HttpService httpService) {
        addClassName("show-time-track-view");

        add(createTitle());
        email.setErrorMessage("Please enter a valid email address");
        add(email);
        length.setValue(10);
        length.setPlaceholder("e.g. 10");
        add(length);
        add(search);

        createHeader();

        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        grid.addComponentColumn(ptt -> createCard(ptt));
        add(grid);

        search.addClickListener(e -> {
            List<PersonTimeTrack> timeTrackRecords = httpService.getTimeTrackRecordsFrom(email.getValue(), length.getValue());
            grid.setItems(timeTrackRecords);
            Notification.show("Time tracks for " + email.getValue() + " are shown.", 5000, Notification.Position.MIDDLE);
        });
    }

    private void createHeader() {
        HorizontalLayout header = new HorizontalLayout(
                createHeaderColumn("E-Mail"),
                createHeaderColumn("Start Work Time"),
                createHeaderColumn("End Work Time")
        );
        add(header);
    }

    private Component createTitle() {
        return new H1("Show Employee Work Time Track");
    }

    private Component createHeaderColumn(String text) {
        return new H3(text);
    }

    private HorizontalLayout createCard(PersonTimeTrack personTimeTrack) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        Span emailTimeTrack = new Span(personTimeTrack.getEmail());
        emailTimeTrack.addClassName("email");

        Span startTimeTrack = new Span(DateTimeParserUtil.getLocalDateTimeAsRepresentationString(personTimeTrack.getStart()));
        startTimeTrack.addClassName("start");

        Span endTimeTrack = new Span(DateTimeParserUtil.getLocalDateTimeAsRepresentationString(personTimeTrack.getEnd()));
        endTimeTrack.addClassName("end");

        card.add(emailTimeTrack, startTimeTrack, endTimeTrack);
        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        List<PersonTimeTrack> personTimeTracks = httpService.getTimeTrackRecordsFrom(length.getValue());
        grid.setItems(personTimeTracks);

    }

}
