package com.example.master_app.services;




import com.example.master_app.Repository.UtilisateurRepository;
import com.example.master_app.entities.Utilisateur;
import com.example.master_app.enumes.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Récupérer tous les utilisateurs
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // Récupérer un utilisateur par ID
    public Utilisateur getUtilisateurById(int id) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
        return utilisateur.orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    // Récupérer un utilisateur par email (utile pour l'authentification)
    public Utilisateur findByEmail(String email) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
        return utilisateur.orElseThrow(() -> new RuntimeException("Utilisateur avec email " + email + " non trouvé"));
    }

    // Récupérer tous les utilisateurs d'un rôle spécifique
    public List<Utilisateur> findByRole(Role role) {
        return utilisateurRepository.findByRole(role);
    }

    // Créer un nouvel utilisateur
    public Utilisateur createUtilisateur(Utilisateur utilisateur) {
        // Vérification simple : l'email ne doit pas déjà exister
        Optional<Utilisateur> existingUser = utilisateurRepository.findByEmail(utilisateur.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Un utilisateur avec cet email existe déjà");
        }
        return utilisateurRepository.save(utilisateur);
    }

    // Mettre à jour un utilisateur existant
    public Utilisateur updateUtilisateur(int id, Utilisateur utilisateurDetails) {
        Utilisateur utilisateur = getUtilisateurById(id);
        utilisateur.setNom(utilisateurDetails.getNom());
        utilisateur.setEmail(utilisateurDetails.getEmail());
        utilisateur.setMotDePasse(utilisateurDetails.getMotDePasse());
        utilisateur.setRole(utilisateurDetails.getRole());
        return utilisateurRepository.save(utilisateur);
    }

    // Supprimer un utilisateur
    public void deleteUtilisateur(int id) {
        Utilisateur utilisateur = getUtilisateurById(id);
        utilisateurRepository.delete(utilisateur);
    }
}