package com.smaek.smaek;

import com.smaek.smaek.model.User;
import com.smaek.smaek.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User tidak ditemukan: " + username));

        return new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            user.isAktif(),
            true, true, true,
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
