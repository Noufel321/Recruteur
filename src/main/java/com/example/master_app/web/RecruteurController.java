package com.example.master_app.web;

import com.example.master_app.Repository.DisponibiliteRecruteurRepository;
import com.example.master_app.Repository.UtilisateurRepository;
import com.example.master_app.entities.DisponibiliteRecruteur;
import com.example.master_app.entities.Recruteur;
import com.example.master_app.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/recruteur")
public class RecruteurController {

    @Autowired
    private DisponibiliteRecruteurRepository disponibiliteRecruteurRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    // Afficher le formulaire de disponibilités
    @GetMapping("/disponibilites")
    public String afficherFormulaireDisponibilite(Model model) {
        Recruteur recruteur = getRecruteurConnecte();

        if (recruteur != null) {
            model.addAttribute("disponibilites", disponibiliteRecruteurRepository.findByRecruteur(recruteur));
            model.addAttribute("disponibilite", new DisponibiliteRecruteur());
        }

        return "recruteur/formulaire_disponibilite";
    }

    // Enregistrer une nouvelle disponibilité
    @PostMapping("/disponibilites")
    public String enregistrerDisponibilite(@ModelAttribute DisponibiliteRecruteur disponibilite, Model model) {
        Recruteur recruteur = getRecruteurConnecte();

        if (recruteur != null) {
            disponibilite.setRecruteur(recruteur);
            disponibiliteRecruteurRepository.save(disponibilite);

            model.addAttribute("disponibilites", disponibiliteRecruteurRepository.findByRecruteur(recruteur));
            model.addAttribute("disponibilite", new DisponibiliteRecruteur());
        }

        return "recruteur/formulaire_disponibilite";
    }

    // Modifier une disponibilité
    @GetMapping("/disponibilites/modify/{id}")
    public String modifierDisponibilite(@PathVariable Long id, Model model) {
        DisponibiliteRecruteur disponibilite = disponibiliteRecruteurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disponibilité invalide"));
        model.addAttribute("disponibilite", disponibilite);
        return "recruteur/formulaire_disponibilite";
    }

    // Enregistrer la modification d'une disponibilité
    @PostMapping("/disponibilites/modify/{id}")
    public String enregistrerModificationDisponibilite(@PathVariable Long id,
                                                       @ModelAttribute DisponibiliteRecruteur disponibiliteModifiee,
                                                       Model model) {
        DisponibiliteRecruteur disponibilite = disponibiliteRecruteurRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disponibilité invalide"));

        disponibilite.setJour(disponibiliteModifiee.getJour());
        disponibilite.setHeureDebut(disponibiliteModifiee.getHeureDebut());
        disponibilite.setHeureFin(disponibiliteModifiee.getHeureFin());

        disponibiliteRecruteurRepository.save(disponibilite);

        Recruteur recruteur = getRecruteurConnecte();
        if (recruteur != null) {
            model.addAttribute("disponibilites", disponibiliteRecruteurRepository.findByRecruteur(recruteur));
        }

        model.addAttribute("disponibilite", new DisponibiliteRecruteur());
        return "recruteur/formulaire_disponibilite";
    }

    // Supprimer une disponibilité
    @PostMapping("/disponibilites/delete/{id}")
    public String supprimerDisponibilite(@PathVariable Long id, Model model) {
        disponibiliteRecruteurRepository.deleteById(id);

        Recruteur recruteur = getRecruteurConnecte();
        if (recruteur != null) {
            model.addAttribute("disponibilites", disponibiliteRecruteurRepository.findByRecruteur(recruteur));
        }

        model.addAttribute("disponibilite", new DisponibiliteRecruteur());
        return "recruteur/formulaire_disponibilite";
    }

    // Page de connexion du recruteur
    @GetMapping("/login")
    public String loginRecruteur(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("message", "Déconnexion réussie");
        }
        return "recruteur/login_recruteur";
    }

    @PostMapping("/login")
    public String loginRecruteurPost() {
        return "recruteur/login_recruteur";
    }

    // Page d'accueil du recruteur
    @GetMapping("/home")
    public String homeRecruteur() {
        return "recruteur/home_recruteur";
    }

    // Méthode utilitaire pour récupérer le recruteur connecté
    private Recruteur getRecruteurConnecte() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByEmail(user.getUsername());

            if (optionalUtilisateur.isPresent() && optionalUtilisateur.get() instanceof Recruteur) {
                return (Recruteur) optionalUtilisateur.get();
            }
        }
        return null;
    }
}
