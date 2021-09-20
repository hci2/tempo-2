package com.tempo2.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
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

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

}
