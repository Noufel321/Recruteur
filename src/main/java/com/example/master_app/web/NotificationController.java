package com.example.master_app.web;

import com.example.master_app.entities.Notification;
import com.example.master_app.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public String getAllNotifications(Model model) {
        List<Notification> notifications = notificationService.getAllNotifications();
        model.addAttribute("notifications", notifications);
        return "notification/list"; // fichier list.html dans dossier templates/notification/
    }

    @GetMapping("/entretien/{entretienId}")
    public String getNotificationsByEntretienId(@PathVariable int entretienId, Model model) {
        List<Notification> notifications = notificationService.getNotificationsByEntretienId(entretienId);
        model.addAttribute("notifications", notifications);
        model.addAttribute("entretienId", entretienId);
        return "notification/list";
    }
}
