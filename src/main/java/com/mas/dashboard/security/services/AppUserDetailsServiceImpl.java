package com.mas.dashboard.security.services;

import com.mas.dashboard.entity.AppUser;
import com.mas.dashboard.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AppUserRepository appUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User Not Found with email: " + email));

        return AppUserDetailsImpl.build(appUser);
    }
}
