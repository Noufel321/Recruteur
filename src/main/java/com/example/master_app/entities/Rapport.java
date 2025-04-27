package com.example.master_app.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;


@Entity
@Table(name = "rapport")
@Getter
@Setter
@NoArgsConstructor
public class Rapport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public void genererPDF() {
        // Logique pour générer un PDF
    }

    public void exporterExcel() {
        // Logique pour exporter en Excel
    }
}