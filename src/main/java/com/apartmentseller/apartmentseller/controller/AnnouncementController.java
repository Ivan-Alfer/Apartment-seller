package com.apartmentseller.apartmentseller.controller;

import com.apartmentseller.apartmentseller.domain.Announcement;
import com.apartmentseller.apartmentseller.dto.AnnouncementDto;
import com.apartmentseller.apartmentseller.services.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    public List<AnnouncementDto> getAllAnnouncement() {
        return announcementService.getAllAnnouncement();
    }

    @GetMapping("{id}")
    public Announcement getAnnouncement(@PathVariable("id") Announcement announcement) {
        return announcement;
    }

    @PostMapping
    public AnnouncementDto addAnnouncement(@RequestBody AnnouncementDto announcement) {
        return announcementService.addAnnouncement(announcement);
    }

    @PutMapping("{id}")
    public AnnouncementDto updateAnnouncement(@PathVariable("id") AnnouncementDto announcementFromDB, @RequestBody AnnouncementDto announcement) {
        return announcementService.updateAnnouncement(announcementFromDB, announcement);
    }

    @DeleteMapping("{id}")
    public void deleteAnnouncement(@PathVariable("id") AnnouncementDto announcement){
        announcementService.deleteAnnouncement(announcement);
    }

}
