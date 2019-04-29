package com.apartmentseller.apartmentseller.controller;

import com.apartmentseller.apartmentseller.domain.Announcement;
import com.apartmentseller.apartmentseller.repository.AnnouncementRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("announcement")
public class AnnouncementController {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @GetMapping
    public List<Announcement> list() {
        return announcementRepository.findAll();
    }

    @GetMapping("{id}")
    public Announcement getAnnouncement(@PathVariable("id") Announcement announcement) {
        return announcement;
    }

    @PostMapping
    public Announcement addAnnouncement(@RequestBody Announcement announcement) {
        announcement.setCreationTime(LocalDateTime.now());
        return announcementRepository.save(announcement);
    }

    @PutMapping("{id}")
    public Announcement updateAnnouncement(@PathVariable("id") Announcement announcementFromDB, @RequestBody Announcement announcement) {
        BeanUtils.copyProperties(announcement, announcementFromDB, "id");
        return announcementRepository.save(announcementFromDB);
    }

    @DeleteMapping("{id}")
    public void deleteAnnouncement(@PathVariable("id") Announcement announcement){
        announcementRepository.delete(announcement);
    }

}
