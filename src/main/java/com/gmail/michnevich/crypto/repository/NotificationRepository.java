package com.gmail.michnevich.crypto.repository;

import com.gmail.michnevich.crypto.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByIsDoneFalse();

}
