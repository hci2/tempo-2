package com.tempo2.application.service.impl;

import com.sun.jna.platform.unix.solaris.LibKstat;
import com.tempo2.application.dto.PersonTimeTrack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class HttpServiceImpl implements HttpService{

    private final String BASE_URL = "http://localhost:8080/records";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void postTimeTrackRecord(PersonTimeTrack personTimeTrack) {
        log.info("Invocation of method postTimeTrackRecord(PersonTimeTrack personTimeTrack)");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", personTimeTrack.getEmail());
        formData.add("start", personTimeTrack.getStartAsLegacyString());
        formData.add("end", personTimeTrack.getEndAsLegacyString());

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, headers);

//        TODO: Fix exception for binding and getting 400 (in Postman it is working with Content-Length and Host Header, https://stackoverflow.com/questions/38372422/how-to-post-form-data-with-spring-resttemplate https://stackoverflow.com/questions/65255379/spring-webclient-post-and-content-length-header-for-application-x-www-form-urlen
//        org.springframework.web.client.HttpClientErrorException$BadRequest: 400 : [{
//            "timestamp" : "2021-09-19T11:51:16.650+0000",
//                    "status" : 400,
//                    "error" : "Bad Request",
//                    "exception" : "org.springframework.validation.BindException",
//                    "errors" : [ {
//                "codes" : [ "typeMis... (1701 bytes)]
//                at org.springframework.web.client.HttpClientErrorException.create(HttpClientErrorException.java:101) ~[spring-web-5.3.9.jar:5.3.9]

        ResponseEntity<String> responseEntity = restTemplate.exchange(BASE_URL,
                HttpMethod.POST,
                httpEntity,
                String.class);
        final String body = responseEntity.getBody();
        log.info("Status code: {}, body size: {}, ", responseEntity.getStatusCodeValue(), body);
    }

    @Override
    public List<PersonTimeTrack> getTimeTrackRecordsFrom(String email, Integer length) {
        log.info("Invocation of method getTimeTrackRecordsFrom(String email, Integer length)");

        ResponseEntity<List<PersonTimeTrack>> responseEntity = restTemplate.exchange(BASE_URL + "?email={email}&length={length}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonTimeTrack>>() {},
                email,
                length);
        List<PersonTimeTrack> body = responseEntity.getBody();
        log.info("Status code: {}, body size: {}, ", responseEntity.getStatusCodeValue(), body.size());
        return body;
    }

    @Override
    public List<PersonTimeTrack> getTimeTrackRecordsFrom(String email) {
        log.info("Invocation of method getTimeTrackRecordsFrom(String email)");

        ResponseEntity<List<PersonTimeTrack>> responseEntity = restTemplate.exchange(BASE_URL + "?email={email}", //&length={length}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonTimeTrack>>() {},
                email);//,
                //10);
        List<PersonTimeTrack> body = responseEntity.getBody();
        log.info("Status code: {}, body size: {}, ", responseEntity.getStatusCodeValue(), body.size());
        return body;
    }

    @Override
    public List<PersonTimeTrack> getTimeTrackRecordsFrom(Integer length) {
        log.info("Invocation of method getTimeTrackRecordsFrom(Integer length)");
        ResponseEntity<List<PersonTimeTrack>> responseEntity = restTemplate.exchange(BASE_URL + "?offset={offset}&length={length}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PersonTimeTrack>>() {},
                0,
                length);
        List<PersonTimeTrack> body = responseEntity.getBody();
        log.info("Status code: {}, body size: {}, ", responseEntity.getStatusCodeValue(), body.size());
        return body;
    }

}
