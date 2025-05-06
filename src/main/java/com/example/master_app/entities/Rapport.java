package com.example.master_app.entities;

import lombok.*;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rapport")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rapport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(columnDefinition = "TEXT")
    private String contenu;

    @ManyToOne
    @JoinColumn(name = "entretien_id")
    private Entretien entretien;

    public void genererPDF() {
        // Logique à implémenter dans RapportService avec iText
    }

    public void exporterExcel() {
        // Logique à implémenter dans RapportService avec Apache POI
    }
}