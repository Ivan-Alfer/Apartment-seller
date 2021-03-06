package com.apartmentseller.apartmentseller.controller;

import com.apartmentseller.apartmentseller.dto.AnnouncementDto;
import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.services.AnnouncementService;
import com.apartmentseller.apartmentseller.services.exceptions.AnnouncementNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public AnnouncementDto getAnnouncement(@PathVariable("id") long announcementId) {
        return announcementService.getAnnouncement(announcementId)
                .orElseThrow(AnnouncementNotFoundException::new);
    }

    @PostMapping
    public AnnouncementDto addAnnouncement(@RequestPart AnnouncementDto announcement,
                                           @AuthenticationPrincipal UserDto userDto,
                                           @RequestParam(required = false, value = "file") MultipartFile file) {
        announcement.setAuthor(userDto);
        return announcementService.addAnnouncement(announcement, file);
    }

    @PutMapping("{id}")
    public AnnouncementDto updateAnnouncement(@PathVariable("id") long announcementId,
                                              @RequestBody AnnouncementDto announcement,
                                              @AuthenticationPrincipal UserDto userDto) {
        return announcementService.updateAnnouncement(announcementId, announcement, userDto);
    }

    @DeleteMapping("{id}")
    public void deleteAnnouncement(@PathVariable("id") long announcementId, @AuthenticationPrincipal UserDto currentUser) {
        announcementService.deleteAnnouncement(announcementId, currentUser);
    }

}
