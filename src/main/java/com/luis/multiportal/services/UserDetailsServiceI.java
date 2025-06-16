package com.luis.multiportal.services;

import com.luis.multiportal.models.User;
import com.luis.multiportal.repositoreis.UserRepository;
import com.luis.multiportal.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceI implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        User entity = this.userRepository.findByUser(user);

        if (Objects.isNull(entity)) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + user);
        }

        return new UserSpringSecurity(
                entity.getId(),
                entity.getUser(),
                entity.getPassword(),
                entity.getProfiles()
        );
    }
}

