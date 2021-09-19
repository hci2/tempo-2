package com.tempo2.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PersonTimeTrack {

    private String email;
    private LocalDateTime start;
    private LocalDateTime end;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public String getStartAsLegacyString() {
        return start.toString().replace("T", " ").replace("-", ".");
    }

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getEndAsLegacyString() {
        return end.toString().replace("T", " ").replace("-", ".");
    }

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    @Override
    public String toString() {
        String s = "{\n" +
                "\"start\": \"" + start.toString() +"\",\n" +
                "\"end\": \"" + end.toString() +"\",\n" +
                "\"email\": \"" + email +"\"\n" +
                "}";
        return s;
    }
}
