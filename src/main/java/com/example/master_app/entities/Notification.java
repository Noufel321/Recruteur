package com.example.master_app.entities;

import com.example.master_app.enumes.Type;
import jakarta.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @ManyToOne
    @JoinColumn(name = "entretien_id", nullable = false)
    private Entretien entretien;

    // Constructeur par défaut
    public Notification() {
    }

    // Constructeur avec tous les champs
    public Notification(int id, Type type, String contenu, Entretien entretien) {
        this.id = id;
        this.type = type;
        this.contenu = contenu;
        this.entretien = entretien;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Entretien getEntretien() {
        return entretien;
    }

    public void setEntretien(Entretien entretien) {
        this.entretien = entretien;
    }

    public void envoyerNotification() {
        // Logique à implémenter dans NotificationService
    }
}