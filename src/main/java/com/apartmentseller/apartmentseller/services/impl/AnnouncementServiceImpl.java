package com.apartmentseller.apartmentseller.services.impl;

import com.apartmentseller.apartmentseller.domain.Announcement;
import com.apartmentseller.apartmentseller.dto.AnnouncementDto;
import com.apartmentseller.apartmentseller.repository.AnnouncementRepository;
import com.apartmentseller.apartmentseller.services.AnnouncementService;
import com.apartmentseller.apartmentseller.services.MapperService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    private final MapperService mapperService;

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository, MapperService mapperService) {
        this.announcementRepository = announcementRepository;
        this.mapperService = mapperService;
    }

    public List<AnnouncementDto> getAllAnnouncement() {
        return announcementRepository.findAll().stream()
                .map(announcementEntity -> mapperService.mapEntityWithDto(announcementEntity, new AnnouncementDto()))
                .collect(Collectors.toList());
    }

    public AnnouncementDto addAnnouncement(AnnouncementDto announcementDto) {
        Announcement announcement = mapperService.mapEntityWithDto(announcementDto, new Announcement());
        announcement.setCreationTime(LocalDateTime.now());
        announcementRepository.save(announcement);
        return announcementDto;
    }

    public AnnouncementDto updateAnnouncement(AnnouncementDto announcementFromDB, AnnouncementDto announcement) {
        BeanUtils.copyProperties(announcement, announcementFromDB, "id");
        Announcement announcementEntity = mapperService.mapEntityWithDto(announcementFromDB,new Announcement());
        announcementRepository.save(announcementEntity);
        return announcementFromDB;
    }

    public void deleteAnnouncement(AnnouncementDto announcementDto) {
        Announcement announcement = mapperService.mapEntityWithDto(announcementDto, new Announcement());
        announcementRepository.delete(announcement);
    }
}
