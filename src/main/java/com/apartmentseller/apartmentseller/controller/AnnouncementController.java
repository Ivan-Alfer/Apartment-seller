package com.apartmentseller.apartmentseller.controller;

import com.apartmentseller.apartmentseller.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AnnouncementController {

    private final UserService userService;

    @Autowired
    public AnnouncementController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/announcement")
    public Map<Long, String> getAuthorsOfTheAnnouncements(@RequestParam("usersId") List<Long> usersId) {
        return userService.getAllAuthors(usersId);
    }

//    @GetMapping("{id}")
//    public AnnouncementDto getAnnouncement(@PathVariable("id") long announcementId) {
//        return announcementService.getAnnouncement(announcementId)
//                .orElseThrow(AnnouncementNotFoundException::new);
//    }
//
//    @PostMapping
//    public AnnouncementDto addAnnouncement(@RequestPart AnnouncementDto announcement,
//                                           @AuthenticationPrincipal UserDto userDto,
//                                           @RequestParam(required = false, value = "file") MultipartFile file) {
//        announcement.setAuthor(userDto);
//        return announcementService.addAnnouncement(announcement, file);
//    }
//
//    @PutMapping("{id}")
//    public AnnouncementDto updateAnnouncement(@PathVariable("id") long announcementId,
//                                              @RequestBody AnnouncementDto announcement,
//                                              @AuthenticationPrincipal UserDto userDto) {
//        return announcementService.updateAnnouncement(announcementId, announcement, userDto);
//    }
//
//    @DeleteMapping("{id}")
//    public void deleteAnnouncement(@PathVariable("id") long announcementId, @AuthenticationPrincipal UserDto currentUser) {
//        announcementService.deleteAnnouncement(announcementId, currentUser);
//    }

}
