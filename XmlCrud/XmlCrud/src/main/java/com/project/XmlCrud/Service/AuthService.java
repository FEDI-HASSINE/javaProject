package com.project.XmlCrud.Service;

import com.project.XmlCrud.Model.Account;
import com.project.XmlCrud.Security.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final CitoyenService citoyenService;
    private final AgentService agentService;
    private final ChefGeneraleService chefGeneraleService;
    private final SecretaireService secretaireService;
    private final ChefInformatiqueService chefInfoService;

    public AuthService(JwtUtil jwtUtil,
                       CitoyenService citoyenService,
                       AgentService agentService,
                       ChefGeneraleService chefGeneraleService,
                       SecretaireService secretaireService,
                       ChefInformatiqueService chefInfoService) {
        this.jwtUtil = jwtUtil;
        this.citoyenService = citoyenService;
        this.agentService = agentService;
        this.chefGeneraleService = chefGeneraleService;
        this.secretaireService = secretaireService;
        this.chefInfoService = chefInfoService;
    }

    public String login(String email, String password) {
        Account account = findAccountByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Email ou mot de passe incorrect"));

        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("Email ou mot de passe incorrect");
        }

        return jwtUtil.generateToken(account.getEmail(), account.getRole());
    }

    private Optional<? extends Account> findAccountByEmail(String email) {
        Optional<? extends Account> user = citoyenService.getAllCitoyens().stream()
                .filter(c -> c.getEmail().equalsIgnoreCase(email))
                .findFirst();
        if (user.isPresent()) {
            return user;
        }

        user = agentService.getAllAgents().stream()
                .filter(a -> a.getEmail().equalsIgnoreCase(email))
                .findFirst();
        if (user.isPresent()) {
            return user;
        }

        user = chefGeneraleService.getAllChefs().stream()
                .filter(cg -> cg.getEmail().equalsIgnoreCase(email))
                .findFirst();
        if (user.isPresent()) {
            return user;
        }

        user = secretaireService.getAllSecretaires().stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email))
                .findFirst();
        if (user.isPresent()) {
            return user;
        }

        return chefInfoService.getAllChefs().stream()
                .filter(ci -> ci.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }
}