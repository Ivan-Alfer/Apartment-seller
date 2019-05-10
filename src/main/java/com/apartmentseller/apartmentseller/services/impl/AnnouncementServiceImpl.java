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

    @Autowired
    public AnnouncementServiceImpl(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    public List<AnnouncementDto> getAllAnnouncement() {
        return announcementRepository.findAll().stream()
                .map(MapperService.INSTANCE::announcementEntityMapToAnnouncementDto)
                .collect(Collectors.toList());
    }

    public AnnouncementDto addAnnouncement(AnnouncementDto announcementDto) {
        Announcement announcement = MapperService.INSTANCE.announcementDtoMapToAnnouncementEntity(announcementDto);
        announcement.setCreationTime(LocalDateTime.now());
        announcementRepository.save(announcement);
        return announcementDto;
    }

    public AnnouncementDto updateAnnouncement(AnnouncementDto announcementFromDB, AnnouncementDto announcement) {
        BeanUtils.copyProperties(announcement, announcementFromDB, "id");
        Announcement announcementEntity = MapperService.INSTANCE.announcementDtoMapToAnnouncementEntity(announcementFromDB);
        announcementRepository.save(announcementEntity);
        return announcementFromDB;
    }

    public void deleteAnnouncement(AnnouncementDto announcementDto) {
        Announcement announcementEntity = MapperService.INSTANCE.announcementDtoMapToAnnouncementEntity(announcementDto);
        announcementRepository.delete(announcementEntity);
    }
}
