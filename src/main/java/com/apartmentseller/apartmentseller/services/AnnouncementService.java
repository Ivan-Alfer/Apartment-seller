package com.apartmentseller.apartmentseller.services;

import com.apartmentseller.apartmentseller.domain.Announcement;
import com.apartmentseller.apartmentseller.repository.AnnouncementRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<Announcement> getAllAnnouncement() {
        return announcementRepository.findAll();
    }

    public Announcement addAnnouncement(Announcement announcement) {
        announcement.setCreationTime(LocalDateTime.now());
        return announcementRepository.save(announcement);
    }

    public Announcement updateAnnouncement(Announcement announcementFromDB, Announcement announcement) {
        BeanUtils.copyProperties(announcement, announcementFromDB, "id");
        return announcementRepository.save(announcementFromDB);
    }

    public void deleteAnnouncement(Announcement announcement) {
        announcementRepository.delete(announcement);
    }
}
