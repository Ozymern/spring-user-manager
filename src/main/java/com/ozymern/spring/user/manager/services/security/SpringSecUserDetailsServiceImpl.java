package com.ozymern.spring.user.manager.services.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ozymern.spring.user.manager.domains.entities.UserEntity;
import com.ozymern.spring.user.manager.repositories.UserRepository;

@Service
public class SpringSecUserDetailsServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(SpringSecUserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       
    	UserEntity user = userRepository.findByEmail(email);
    	
    	System.err.println(user);

        if (email == null) {
            logger.error("Error login: no existe el usuario " + email);
            throw new UsernameNotFoundException("Username " + email + " no existe!");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().toUpperCase()));
        });


        if (authorities.isEmpty()) {
            logger.error("Error login: no existe el usuario " + email + " no tiene roles asignados");
            throw new UsernameNotFoundException(
                    "Error login: no existe el usuario " + email + " no tiene roles asignados");
        }

        System.err.println(email + user.getPassword() + user.getEnabled() + authorities);

        return new User(email, user.getPassword(), user.getEnabled(), true, true, true, authorities);
    }
}
