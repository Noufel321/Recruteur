package com.example.master_app.services;

import com.example.master_app.Repository.EntretienRepository;
import com.example.master_app.entities.Entretien;
import com.example.master_app.enumes.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EntretienService {

    @Autowired
    private EntretienRepository entretienRepository;

    @Autowired
    private NotificationService notificationService;

    // Récupérer tous les entretiens
    public List<Entretien> getAllEntretiens() {
        return entretienRepository.findAll();
    }

    // Récupérer un entretien par ID
    public Entretien getEntretienById(int id) {
        Optional<Entretien> entretien = entretienRepository.findById(id);
        return entretien.orElseThrow(() -> new RuntimeException("Entretien non trouvé"));
    }

    // Créer un nouvel entretien et envoyer une notification
    public Entretien createEntretien(Entretien entretien) {
        // Sauvegarder l'entretien
        Entretien savedEntretien = entretienRepository.save(entretien);

        // Envoyer une notification au candidat
        String message = "Votre entretien est planifié le " + entretien.getDate() + " à " + entretien.getLieu();
        try {
            notificationService.createAndSendNotification(savedEntretien, Type.EMAIL, message);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de l'envoi de la notification: " + e.getMessage());
        }

        return savedEntretien;
    }

    // Mettre à jour un entretien existant
    public Entretien updateEntretien(int id, Entretien entretienDetails) {
        Entretien entretien = getEntretienById(id);
        entretien.setDate(entretienDetails.getDate());
        entretien.setLieu(entretienDetails.getLieu());
        entretien.setStatut(entretienDetails.getStatut());
        return entretienRepository.save(entretien);
    }

    // Supprimer un entretien
    public void deleteEntretien(int id) {
        Entretien entretien = getEntretienById(id);
        entretienRepository.delete(entretien);
    }
}