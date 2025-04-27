package com.example.master_app.entities;

import com.example.master_app.enumes.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;



    @ManyToOne
    @JoinColumn(name = "entretien_id", nullable = false)
    private Entretien entretien;

    public void envoyerNotification() {
        // Logique pour envoyer une notification
    }
}
