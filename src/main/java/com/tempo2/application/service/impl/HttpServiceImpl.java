package com.tempo2.application.service.impl;

import com.tempo2.application.dto.PersonTimeTrack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HttpServiceImpl implements HttpService{


    @Override
    public void postTimeTrackRecord(PersonTimeTrack personTimeTrack) {
        log.info("Invocation of method postTimeTrackRecord(PersonTimeTrack personTimeTrack)");
    }

    @Override
    public List<PersonTimeTrack> getTimeTrackRecordsFrom(String email, Integer length) {
        log.info("Invocation of method getTimeTrackRecordsFrom(String email, Integer length)");
        return null;
    }

    @Override
    public List<PersonTimeTrack> getTimeTrackRecordsFrom(String email) {
        log.info("Invocation of method getTimeTrackRecordsFrom(String email)");
        return null;
    }

    @Override
    public List<PersonTimeTrack> getTimeTrackRecordsFrom(Integer length) {
        log.info("Invocation of method getTimeTrackRecordsFrom(Integer length)");
        return null;
    }

}
