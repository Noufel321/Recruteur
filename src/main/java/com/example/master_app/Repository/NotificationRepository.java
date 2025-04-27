package com.example.master_app.Repository;

import com.example.master_app.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    // Requête personnalisée pour trouver toutes les notifications associées à un entretien
    List<Notification> findByEntretienId(int entretienId);
}