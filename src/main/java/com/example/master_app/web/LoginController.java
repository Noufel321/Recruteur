package com.example.master_app.web;

import com.example.master_app.entities.Utilisateur;
import com.example.master_app.enumes.Role;
import com.example.master_app.Repository.UtilisateurRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private AuthenticationManager authenticationManager;



    @PostMapping("/process-login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {

        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(username);

        if (utilisateurOpt.isEmpty() || utilisateurOpt.get().getRole() != Role.RECRUTEUR) {
            redirectAttributes.addFlashAttribute("error", "Accès réservé aux recruteurs");
            return "redirect:/login?error";
        }

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            Authentication auth = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(auth);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            return "redirect:/recruteur/home_recruteur";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Email ou mot de passe incorrect");
            return "redirect:/login?error";
        }
    }
}
