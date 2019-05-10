package com.apartmentseller.apartmentseller.services;

import com.apartmentseller.apartmentseller.domain.Announcement;
import com.apartmentseller.apartmentseller.domain.User;
import com.apartmentseller.apartmentseller.dto.AnnouncementDto;
import com.apartmentseller.apartmentseller.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MapperService {

    MapperService INSTANCE = Mappers.getMapper(MapperService.class);

    UserDto userEntityMapToUserDto(User user);

    User userDtoMapToUserEntity(UserDto userDto);

    AnnouncementDto announcementEntityMapToAnnouncementDto(Announcement announcement);

    Announcement announcementDtoMapToAnnouncementEntity(AnnouncementDto announcementDto);
}
