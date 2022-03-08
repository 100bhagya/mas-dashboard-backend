package com.mas.dashboard.repository;

import com.mas.dashboard.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
  Optional<AppUser> findByUsername(final String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
