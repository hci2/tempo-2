package com.tempo2.application.service.impl;

import com.tempo2.application.dto.PersonTimeTrack;
import com.tempo2.application.util.DateTimeParserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HttpServiceImpl implements HttpService {

    @Value("${legacy-backend.base-url:http://localhost:8080/records}")
    private String BASE_URL;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void postTimeTrackRecord(PersonTimeTrack personTimeTrack) {
        log.debug("Invocation of method postTimeTrackRecord(PersonTimeTrack personTimeTrack)");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("email", personTimeTrack.getEmail());
        formData.add("start", DateTimeParserUtil.getLocalDateTimeAsLegacyString(personTimeTrack.getStart()));
        formData.add("end", DateTimeParserUtil.getLocalDateTimeAsLegacyString(personTimeTrack.getEnd()));

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(formData, headers);

        ResponseEntity<PersonTimeTrack> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(BASE_URL,
                    HttpMethod.POST,
                    httpEntity,
                    PersonTimeTrack.class);
        } catch (HttpStatusCodeException httpStatusCodeException) {
            log.warn("Exception occurred during post call: ", httpStatusCodeException);
            return;
        }
        final PersonTimeTrack body = responseEntity.getBody();
        log.debug("Status code: {}, body: {}", responseEntity.getStatusCodeValue(), body);
        log.info("Created record: {}", body);
    }

    @Override
    public List<PersonTimeTrack> getTimeTrackRecordsFrom(String email, Integer length) {
        log.debug("Invocation of method getTimeTrackRecordsFrom(String email, Integer length)");

        ResponseEntity<List<PersonTimeTrack>> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(BASE_URL + "?email={email}&length={length}",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<PersonTimeTrack>>() {
                    },
                    email,
                    length);
        } catch (HttpStatusCodeException httpStatusCodeException) {
            log.warn("Exception occurred during get call: ", httpStatusCodeException);
            return Collections.emptyList();
        }

        List<PersonTimeTrack> body = responseEntity.getBody();
        body = body.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());

        log.debug("Status code: {}, body size: {}", responseEntity.getStatusCodeValue(), body.size());
        log.info("Retrieved records: {}, for: {}", body.size(), email);
        return body;
    }

    @Override
    public List<PersonTimeTrack> getTimeTrackRecordsFrom(String email) {
        log.debug("Invocation of method getTimeTrackRecordsFrom(String email)");

        ResponseEntity<List<PersonTimeTrack>> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(BASE_URL + "?email={email}", //&length={length}",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<PersonTimeTrack>>() {
                    },
                    email);//,
            //10);
        } catch (HttpStatusCodeException httpStatusCodeException) {
            log.warn("Exception occurred during get call: ", httpStatusCodeException);
            return Collections.emptyList();
        }

        List<PersonTimeTrack> body = responseEntity.getBody();
        body = body.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());

        log.debug("Status code: {}, body size: {}", responseEntity.getStatusCodeValue(), body.size());
        log.info("Retrieved records: {}, for: {}", body.size(), email);
        return body;
    }

    @Override
    public List<PersonTimeTrack> getTimeTrackRecordsFrom(Integer length) {
        log.debug("Invocation of method getTimeTrackRecordsFrom(Integer length)");

        ResponseEntity<List<PersonTimeTrack>> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(BASE_URL + "?offset={offset}&length={length}",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<PersonTimeTrack>>() {
                    },
                    0,
                    length);
        } catch (HttpStatusCodeException httpStatusCodeException) {
            log.warn("Exception occurred during get call: ", httpStatusCodeException);
            return Collections.emptyList();
        }

        List<PersonTimeTrack> body = responseEntity.getBody();
        body = body.parallelStream().filter(Objects::nonNull).collect(Collectors.toList());

        log.debug("Status code: {}, body size: {}", responseEntity.getStatusCodeValue(), body.size());
        log.info("Retrieved records: {}", body.size());
        return body;
    }

}
