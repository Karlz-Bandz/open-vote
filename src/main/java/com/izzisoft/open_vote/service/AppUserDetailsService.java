package com.izzisoft.open_vote.service;

import com.izzisoft.open_vote.model.AppUser;
import com.izzisoft.open_vote.model.AppUserRole;
import com.izzisoft.open_vote.repo.AppUserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final AppUserRepo appUserRepo;

    public AppUserDetailsService(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser foundUser = appUserRepo.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found!")
        );

        return new User(foundUser.getEmail(), foundUser.getPassword(), mapRoles(foundUser.getRoles()));
    }

    private Collection<SimpleGrantedAuthority> mapRoles(Set<AppUserRole> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .toList();
    }
}
