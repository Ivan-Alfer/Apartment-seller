package com.apartmentseller.apartmentseller.controller;

import com.apartmentseller.apartmentseller.domain.Announcement;
import com.apartmentseller.apartmentseller.services.AnnouncementService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("announcement")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    public List<Announcement> getAllAnnouncement() {
        return announcementService.getAllAnnouncement();
    }

    @GetMapping("{id}")
    public Announcement getAnnouncement(@PathVariable("id") Announcement announcement) {
        return announcement;
    }

    @PostMapping
    public Announcement addAnnouncement(@RequestBody Announcement announcement) {
        return announcementService.addAnnouncement(announcement);
    }

    @PutMapping("{id}")
    public Announcement updateAnnouncement(@PathVariable("id") Announcement announcementFromDB, @RequestBody Announcement announcement) {
        return announcementService.updateAnnouncement(announcementFromDB, announcement);
    }

    @DeleteMapping("{id}")
    public void deleteAnnouncement(@PathVariable("id") Announcement announcement){
        announcementService.deleteAnnouncement(announcement);
    }

}
