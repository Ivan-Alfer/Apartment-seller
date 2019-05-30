package com.apartmentseller.apartmentseller.services.impl;

import com.apartmentseller.apartmentseller.domain.Announcement;
import com.apartmentseller.apartmentseller.dto.AnnouncementDto;
import com.apartmentseller.apartmentseller.dto.UserDto;
import com.apartmentseller.apartmentseller.repository.AnnouncementRepository;
import com.apartmentseller.apartmentseller.services.AnnouncementService;
import com.apartmentseller.apartmentseller.services.MapperService;
import com.apartmentseller.apartmentseller.services.exceptions.AnnouncementNotFoundException;
import com.apartmentseller.apartmentseller.services.exceptions.UserDoesNotHavePermission;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Value("${upload.path}")
    private String uploadPath;

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

    public AnnouncementDto addAnnouncement(AnnouncementDto announcementDto, MultipartFile file) {
        if(Objects.nonNull(file) && !StringUtils.isEmpty(file.getOriginalFilename())){
            addImageToAnnouncement(announcementDto, file);
        }

        Announcement announcement = MapperService.INSTANCE.announcementDtoMapToAnnouncementEntity(announcementDto);
        announcement.setCreationTime(LocalDateTime.now());
        announcementRepository.save(announcement);
        return announcementDto;
    }

    private void addImageToAnnouncement(AnnouncementDto announcementDto, MultipartFile file) {
        File uploadDir = new File(uploadPath);
        if(!uploadDir.exists()){
            uploadDir.mkdir();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + file.getOriginalFilename();

        try {
            file.transferTo(new File(uploadPath + "/" + resultFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        announcementDto.setFilename(resultFileName);
    }

    public AnnouncementDto updateAnnouncement(long announcementId, AnnouncementDto announcement, UserDto currentUser) {
        return announcementRepository.findById(announcementId)
                .map(announcementEntity -> {
                    if(!ServiceUtils.hasUserPermissionToUpdate(announcementEntity.getAuthor().getId(), currentUser)){
                        throw new UserDoesNotHavePermission("You don't have permission");
                    }
                    BeanUtils.copyProperties(announcement, announcementEntity, "id", "author", "creationTime");
                    announcementRepository.save(announcementEntity);
                    return MapperService.INSTANCE.announcementEntityMapToAnnouncementDto(announcementEntity);
                }).orElseThrow(AnnouncementNotFoundException::new);
    }

    public void deleteAnnouncement(long announcementId, UserDto currentUser) {
        announcementRepository.findById(announcementId)
                .map(announcementEntity -> {
                    if(!ServiceUtils.hasUserPermissionToUpdate(announcementEntity.getAuthor().getId(), currentUser)){
                        throw new UserDoesNotHavePermission("");
                    }
                    announcementRepository.delete(announcementEntity);
                    return Optional.empty();
                })
                .orElseThrow(AnnouncementNotFoundException::new);
    }

    @Override
    public Optional<AnnouncementDto> getAnnouncement(long announcementId) {
        return announcementRepository.findById(announcementId)
                .map(MapperService.INSTANCE::announcementEntityMapToAnnouncementDto);
    }
}
