package com.example.master_app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class DisponibiliteRecruteur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruteur_id")
    private Recruteur recruteur;
}
