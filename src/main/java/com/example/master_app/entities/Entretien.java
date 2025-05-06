package com.example.master_app.entities;

import com.example.master_app.enumes.Statut;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "entretien")
public class Entretien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private String lieu;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "recruteur_id", nullable = false)
    private Recruteur recruteur;

    @ManyToOne
    @JoinColumn(name = "candidat_id", nullable = false)
    private Candidat candidat;

    @OneToMany(mappedBy = "entretien")
    private List<Notification> notifications;

    @Column(columnDefinition = "TEXT")
    private String compteRendu;

    // Constructeur par défaut
    public Entretien() {
    }

    // Constructeur avec tous les champs
    public Entretien(int id, LocalDateTime date, String lieu, Statut statut, Recruteur recruteur, Candidat candidat, List<Notification> notifications, String compteRendu) {
        this.id = id;
        this.date = date;
        this.lieu = lieu;
        this.statut = statut;
        this.recruteur = recruteur;
        this.candidat = candidat;
        this.notifications = notifications;
        this.compteRendu = compteRendu;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Recruteur getRecruteur() {
        return recruteur;
    }

    public void setRecruteur(Recruteur recruteur) {
        this.recruteur = recruteur;
    }

    public Candidat getCandidat() {
        return candidat;
    }

    public void setCandidat(Candidat candidat) {
        this.candidat = candidat;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public String getCompteRendu() {
        return compteRendu;
    }

    public void setCompteRendu(String compteRendu) {
        this.compteRendu = compteRendu;
    }

    public void ajouterCompteRendu(String compteRendu) {
        this.compteRendu = compteRendu != null ? compteRendu : this.compteRendu;
    }
}