package com.inmed.security;

import com.inmed.posauth.security.PosUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PosAwareUserDetailsService
        implements UserDetailsService {

    private final CustomUserDetailsService userService;

    private final PosUserDetailsService posService;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        try {

            return userService.loadUserByUsername(username);

        } catch (UsernameNotFoundException ex) {

            return posService.loadByUsername(username);

        }
    }
}