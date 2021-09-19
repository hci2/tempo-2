package com.tempo2.application.views.showtimetrack;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import com.tempo2.application.dto.PersonTimeTrack;
import com.tempo2.application.service.impl.HttpService;
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

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//    Grid<Person> grid = new Grid<>();

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

        HorizontalLayout header = new HorizontalLayout(
                createHeaderColumn("E-Mail"),
                createHeaderColumn("Start Work Time"),
                createHeaderColumn("End Work Time")
        );
        add(header);

        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
//        grid.addComponentColumn(person -> createCard(person));
        grid.addComponentColumn(ptt -> createCard(ptt));
        add(grid);

        search.addClickListener(e -> {
            List<PersonTimeTrack> timeTrackRecords = httpService.getTimeTrackRecordsFrom(email.getValue(), length.getValue());
            grid.setItems(timeTrackRecords);
            Notification.show("Time tracks for " + email.getValue() + " are shown.", 5000, Notification.Position.MIDDLE);
        });
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

        Span startTimeTrack = new Span(personTimeTrack.getStart().format(formatter));
        startTimeTrack.addClassName("start");

        Span endTimeTrack = new Span(personTimeTrack.getEnd().format(formatter));
        endTimeTrack.addClassName("end");

        card.add(emailTimeTrack, startTimeTrack, endTimeTrack);
        return card;
    }

//    private HorizontalLayout createCard(Person person) {
//        HorizontalLayout card = new HorizontalLayout();
//        card.addClassName("card");
//        card.setSpacing(false);
//        card.getThemeList().add("spacing-s");
//
//        Image image = new Image();
//        image.setSrc(person.getImage());
//        VerticalLayout description = new VerticalLayout();
//        description.addClassName("description");
//        description.setSpacing(false);
//        description.setPadding(false);
//
//        HorizontalLayout header = new HorizontalLayout();
//        header.addClassName("header");
//        header.setSpacing(false);
//        header.getThemeList().add("spacing-s");
//
//        Span name = new Span(person.getName());
//        name.addClassName("name");
//        Span date = new Span(person.getDate());
//        date.addClassName("date");
//        header.add(name, date);
//
//        Span post = new Span(person.getPost());
//        post.addClassName("post");
//
//        HorizontalLayout actions = new HorizontalLayout();
//        actions.addClassName("actions");
//        actions.setSpacing(false);
//        actions.getThemeList().add("spacing-s");
//
//        Icon likeIcon = VaadinIcon.HEART.create();
//        likeIcon.addClassName("icon");
//        Span likes = new Span(person.getLikes());
//        likes.addClassName("likes");
//        Icon commentIcon = VaadinIcon.COMMENT.create();
//        commentIcon.addClassName("icon");
//        Span comments = new Span(person.getComments());
//        comments.addClassName("comments");
//        Icon shareIcon = VaadinIcon.CONNECT.create();
//        shareIcon.addClassName("icon");
//        Span shares = new Span(person.getShares());
//        shares.addClassName("shares");
//
//        actions.add(likeIcon, likes, commentIcon, comments, shareIcon, shares);
//
//        description.add(header, post, actions);
//        card.add(image, description);
//        return card;
//    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        List<PersonTimeTrack> personTimeTracks = httpService.getTimeTrackRecordsFrom(length.getValue());
        grid.setItems(personTimeTracks);

        // Set some data when this view is displayed.
//        List<Person> persons = Arrays.asList( //
//                createPerson("https://randomuser.me/api/portraits/men/42.jpg", "John Smith", "May 8",
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/women/42.jpg", "Abagail Libbie", "May 3",
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/men/24.jpg", "Alberto Raya", "May 3",
//
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/women/24.jpg", "Emmy Elsner", "Apr 22",
//
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/men/76.jpg", "Alf Huncoot", "Apr 21",
//
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/women/76.jpg", "Lidmila Vilensky", "Apr 17",
//
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/men/94.jpg", "Jarrett Cawsey", "Apr 17",
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/women/94.jpg", "Tania Perfilyeva", "Mar 8",
//
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/men/16.jpg", "Ivan Polo", "Mar 5",
//
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/women/16.jpg", "Emelda Scandroot", "Mar 5",
//
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/men/67.jpg", "Marcos Sá", "Mar 4",
//
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20"),
//                createPerson("https://randomuser.me/api/portraits/women/67.jpg", "Jacqueline Asong", "Mar 2",
//
//                        "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document without relying on meaningful content (also called greeking).",
//                        "1K", "500", "20")
//
//
//        );
//
//        grid.setItems(persons);
    }

//    private static Person createPerson(String image, String name, String date, String post, String likes,
//            String comments, String shares) {
//        Person p = new Person();
//        p.setImage(image);
//        p.setName(name);
//        p.setDate(date);
//        p.setPost(post);
//        p.setLikes(likes);
//        p.setComments(comments);
//        p.setShares(shares);
//
//        return p;
//    }

}
