package com.apartmentseller.apartmentseller.services;

import com.apartmentseller.apartmentseller.dto.AnnouncementDto;

import java.util.List;

public interface AnnouncementService {

    List<AnnouncementDto> getAllAnnouncement();
    AnnouncementDto addAnnouncement(AnnouncementDto announcementDto);
    AnnouncementDto updateAnnouncement(AnnouncementDto announcementFromDB, AnnouncementDto announcement);
    void deleteAnnouncement(AnnouncementDto announcementDto);
}
