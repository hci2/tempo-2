package com.tempo2.application.dto;

import com.tempo2.application.data.AbstractEntity;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

public class PersonTimeTrack {

    private String email;
    private List<TimeTrack> timeTracks = new ArrayList<>();

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public List<TimeTrack> getTimeTracks() {
        return timeTracks;
    }

    public void setTimeTracks(List<TimeTrack> timeTracks) {
        this.timeTracks = timeTracks;
    }

    public void addTimeTracks(List<TimeTrack> timeTracks) {
        this.timeTracks.addAll(timeTracks);
    }

    public void addTimeTrack(TimeTrack timeTrack) {
        this.timeTracks.add(timeTrack);
    }
}
