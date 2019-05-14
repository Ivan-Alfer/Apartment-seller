package com.apartmentseller.apartmentseller.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AnnouncementDto {

    private long id;
    private String text;
    private UserDto author;
    private LocalDateTime creationTime;
    private String filename;

}
