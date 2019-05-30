package com.apartmentseller.apartmentseller.controller;

import com.apartmentseller.apartmentseller.domain.User;
import com.apartmentseller.apartmentseller.services.AnnouncementService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    private final AnnouncementService announcementService;

    public MainController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    @GetMapping
    @RequestMapping("/")
    public String main(Model model, @AuthenticationPrincipal User user) {
        Map<Object, Object> frontendData = new HashMap<>();
        frontendData.put("profile", user);
        frontendData.put("announcements", announcementService.getAllAnnouncement());
        model.addAttribute("frontendDate", frontendData);
        return "index";
    }
}
