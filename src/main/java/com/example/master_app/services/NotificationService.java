package com.example.master_app.services;

import com.example.master_app.Repository.NotificationRepository;
import com.example.master_app.entities.Entretien;
import com.example.master_app.entities.Notification;
import com.example.master_app.enumes.Type;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Service
public class NotificationService {

    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    @Autowired
    private NotificationRepository notificationRepository;

    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    public NotificationService() {
        logger.info("Initialisation de NotificationService...");
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public List<Notification> getNotificationsByEntretienId(int entretienId) {
        return notificationRepository.findByEntretienId(entretienId);
    }

    public Notification createAndSendNotification(Entretien entretien, Type type, String message) throws IOException {
        if (entretien == null) {
            throw new IllegalArgumentException("L'entretien ne peut pas être null");
        }
        if (entretien.getCandidat() == null || entretien.getCandidat().getEmail() == null) {
            throw new IllegalArgumentException("Le candidat ou son email est manquant pour cet entretien");
        }

        Notification notification = new Notification();
        notification.setEntretien(entretien);
        notification.setType(type);

        Notification savedNotification = notificationRepository.save(notification);

        if (type == Type.EMAIL) {
            sendEmail(entretien.getCandidat().getEmail(), "Notification d'Entretien", message);
        } else if (type == Type.SMS) {
            throw new UnsupportedOperationException("Envoi de SMS non implémenté");
        }

        return savedNotification;
    }

    private void sendEmail(String toEmail, String subject, String content) throws IOException {
        if (apiKey == null || apiKey.isEmpty()) {
            logger.severe("La clé API SendGrid n'est pas configurée");
            throw new IllegalStateException("La clé API SendGrid n'est pas configurée");
        }
        if (fromEmail == null || fromEmail.isEmpty()) {
            logger.severe("L'email expéditeur SendGrid n'est pas configuré");
            throw new IllegalStateException("L'email expéditeur SendGrid n'est pas configuré");
        }

        logger.info("Envoi d'un email à " + toEmail + " depuis " + fromEmail);
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        Content emailContent = new Content("text/plain", content);
        Mail mail = new Mail(from, subject, to, emailContent);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());
        try {
            sg.api(request);
            logger.info("Email envoyé avec succès à " + toEmail);
        } catch (IOException e) {
            logger.severe("Erreur lors de l'envoi de l'email: " + e.getMessage());
            throw new IOException("Erreur lors de l'envoi de l'email: " + e.getMessage(), e);
        }
    }
}