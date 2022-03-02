package com.mas.dashboard.service;

import com.mas.dashboard.entity.AppUser;
import com.mas.dashboard.entity.Role;
import com.mas.dashboard.repository.AppUserRepository;
import com.mas.dashboard.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Log4j2
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

  @Autowired
  private AppUserRepository appUserRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    final Optional<AppUser> optionalAppUser = this.appUserRepository.findByEmail(email);
    if (!optionalAppUser.isPresent()) {
      log.error("User with email {} not found", email);
      throw new UsernameNotFoundException("User not found");
    }
    final AppUser appUser = optionalAppUser.get();
    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
    appUser.getRole().forEach(role -> {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
    });
    return new org.springframework.security.core.userdetails.User(appUser.getEmail(), appUser.getPassword(), authorities);

  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public AppUser saveAppUser(final AppUser appUser) {
    log.info("Saving new user {} to the database", appUser);
    appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
    return this.appUserRepository.save(appUser);
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public Role saveRole(final Role role) {
    log.info("Saving new role {} to the database", role);
    return this.roleRepository.save(role);
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public void addRoleToAppUser(final String email, final String roleName) {
    log.info("Adding new role {} to the user with email {}", roleName, email);
    final Optional<AppUser> optionalAppUser = this.appUserRepository.findByEmail(email);
    if (!optionalAppUser.isPresent()) {
//      throw new RecordNotFoundException();
    }
    final AppUser appUser = optionalAppUser.get();
    final Optional<Role> optionalRole = this.roleRepository.findByName(roleName);
    if (!optionalRole.isPresent()) {
//      throw new RecordNotFoundException();
    }
    appUser.getRole().add(optionalRole.get());
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public AppUser getAppUser(final String email) {
    log.info("Fetching user with email {}", email);
    final Optional<AppUser> optionalAppUser = this.appUserRepository.findByEmail(email);
    if (!optionalAppUser.isPresent()) {
//      throw new RecordNotFoundException();
    }
    return optionalAppUser.get();
  }

  @Override
  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
  public List<AppUser> getAppUsers() {
    log.info("Fetching all users");
    return this.appUserRepository.findAll();
  }
}
