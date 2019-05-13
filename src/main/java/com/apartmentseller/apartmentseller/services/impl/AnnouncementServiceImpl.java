package com.apartmentseller.apartmentseller.services.impl;

import com.apartmentseller.apartmentseller.domain.Announcement;
import com.apartmentseller.apartmentseller.dto.AnnouncementDto;
import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.repository.AnnouncementRepository;
import com.apartmentseller.apartmentseller.services.AnnouncementService;
import com.apartmentseller.apartmentseller.services.MapperService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

    public AnnouncementDto updateAnnouncement(long announcementId, AnnouncementDto announcement, UserDto currentUser) throws Exception {
        return announcementRepository.findById(announcementId)
                .map(announcementEntity -> {
                    if(!ServiceUtils.hasUserPermissionToUpdate(announcementEntity.getAuthor().getId(), currentUser)){
                        return null;
                    }
                    BeanUtils.copyProperties(announcement, announcementEntity, "id", "author", "creationTime");
                    announcementRepository.save(announcementEntity);
                    return announcement;
                }).orElseThrow(Exception::new);
    }

    public void deleteAnnouncement(long announcementId, UserDto currentUser) {
        announcementRepository.findById(announcementId)
                .map(announcementEntity -> {
                    if(!ServiceUtils.hasUserPermissionToUpdate(announcementEntity.getAuthor().getId(), currentUser)){
                        return Optional.empty();
                    }
                    announcementRepository.delete(announcementEntity);
                    return Optional.empty();
                });

//        Announcement announcementEntity = MapperService.INSTANCE.announcementDtoMapToAnnouncementEntity(announcementDto);
//        announcementRepository.delete(announcementEntity);
    }

    @Override
    public Optional<AnnouncementDto> getAnnouncement(long announcementId) {
        return announcementRepository.findById(announcementId).map(MapperService.INSTANCE::announcementEntityMapToAnnouncementDto);
    }
}
