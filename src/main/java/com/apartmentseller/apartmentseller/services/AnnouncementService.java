package com.apartmentseller.apartmentseller.services;

import com.apartmentseller.apartmentseller.dto.AnnouncementDto;
import com.apartmentseller.apartmentseller.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {

    List<AnnouncementDto> getAllAnnouncement();
    AnnouncementDto addAnnouncement(AnnouncementDto announcementDto, MultipartFile file);
    void deleteAnnouncement(long announcementId, UserDto currentUser);
    Optional<AnnouncementDto> getAnnouncement(long announcementId);
    AnnouncementDto updateAnnouncement(long announcementId, AnnouncementDto announcement, UserDto userDto);
}
