package com.example.master_app.web;

import com.example.master_app.entities.Utilisateur;
import com.example.master_app.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping
    public String getAllUtilisateurs(Model model) {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        model.addAttribute("utilisateurs", utilisateurs); // Correction : utiliser "utilisateurs" au lieu de "utilisateur"
        return "utilisateur/list";
    }

    @GetMapping("/new")
    public String showUtilisateurForm(Model model) {
        model.addAttribute("utilisateur", new Utilisateur());
        return "utilisateur/form";
    }

    @PostMapping("/ajt") // Simplifié à /ajt pour plus de cohérence
    public String createUtilisateur(@ModelAttribute Utilisateur utilisateur, Model model) {
        try {
            utilisateurService.createUtilisateur(utilisateur);
            return "redirect:/utilisateur";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la création : " + e.getMessage());
            return "utilisateur/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur == null) {
            model.addAttribute("error", "Utilisateur non trouvé");
            return "redirect:/utilisateur";
        }
        model.addAttribute("utilisateur", utilisateur);
        return "utilisateur/form";
    }

    @PostMapping("/update/{id}")
    public String updateUtilisateur(@PathVariable int id, @ModelAttribute Utilisateur utilisateur, Model model) {
        try {
            utilisateurService.updateUtilisateur(id, utilisateur);
            return "redirect:/utilisateur";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la mise à jour : " + e.getMessage());
            return "utilisateur/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteUtilisateur(@PathVariable int id, Model model) {
        try {
            utilisateurService.deleteUtilisateur(id);
            return "redirect:/utilisateur";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression : " + e.getMessage());
            return "redirect:/utilisateur";
        }
    }
}