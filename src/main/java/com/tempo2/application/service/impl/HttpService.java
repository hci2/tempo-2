package com.tempo2.application.service.impl;

import com.tempo2.application.dto.PersonTimeTrack;

import java.util.List;

public interface HttpService {

    void postTimeTrackRecord(PersonTimeTrack personTimeTrack);

    List<PersonTimeTrack> getTimeTrackRecordsFrom(String email, Integer length);

    List<PersonTimeTrack> getTimeTrackRecordsFrom(String email);

    List<PersonTimeTrack> getTimeTrackRecordsFrom(Integer length);

}
