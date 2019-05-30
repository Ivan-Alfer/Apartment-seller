package com.apartmentseller.apartmentseller.repository;

import com.apartmentseller.apartmentseller.domain.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
