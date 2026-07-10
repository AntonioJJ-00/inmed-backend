package com.inmed.posauth.security;

import com.inmed.PosCredential.entity.PosCredential;
import com.inmed.PosCredential.repository.PosCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PosUserDetailsService {

    private final PosCredentialRepository repository;

    public UserDetails loadByUsername(String username){

        PosCredential credential =
                repository.findByUsername(username)
                        .orElseThrow(() ->
                                new UsernameNotFoundException("POS not found")
                        );

        return new org.springframework.security.core.userdetails.User(

                credential.getUsername(),

                credential.getPassword(),

                credential.getEnabled(),

                true,

                true,

                true,

                List.of(
                        new SimpleGrantedAuthority("ROLE_POS")
                )
        );
    }
}