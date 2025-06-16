package com.luis.multiportal.services;


import com.luis.multiportal.dto.LoginResponseDTO;
import com.luis.multiportal.exceptions.LoginException;
import com.luis.multiportal.exceptions.UserCreationException;
import com.luis.multiportal.models.enuns.RoleEnum;
import com.luis.multiportal.security.JWTUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.luis.multiportal.repositoreis.UserRepository;
import com.luis.multiportal.models.User;
import org.springframework.stereotype.Service;
import com.luis.multiportal.controllers.exeptions.GlobalExptionHandler;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTUtil jwtUtil;

    @Transactional
    public User create(User obj) {

        if (userRepository.findByUser(obj.getUser()) != null) {
            throw new UserCreationException("Já existe um usuário com esse nome");
        }

        if (obj.getPassword() == null || obj.getPassword().length() < 6) {
            throw new UserCreationException("A senha deve ter no mínimo 6 caracteres");
        }

        obj.setId(null);
        obj.setPassword(this.bCryptPasswordEncoder.encode(obj.getPassword()));
        obj.setProfiles(Stream.of(RoleEnum.ADMIN.getCode()).collect(Collectors.toSet()));
        obj = this.userRepository.save(obj);

        return obj;
    }

    public LoginResponseDTO login(String username, String password) {
        User user = userRepository.findByUser(username);
        if (user == null) {
            throw new LoginException("Usuário não encontrado");
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new LoginException("Senha inválida");
        }

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (AuthenticationException e) {
            throw new LoginException("Falha na autenticação");
        }

        String token = jwtUtil.generateToken(username);

        return new LoginResponseDTO(
                user.getUser(),
                token,
                "Login realizado com sucesso"
        );
    }
}
