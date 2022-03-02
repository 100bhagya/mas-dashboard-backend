package com.mas.dashboard.service;

import com.mas.dashboard.entity.AppUser;
import com.mas.dashboard.entity.Role;

import java.util.List;

public interface AppUserService {

  AppUser saveAppUser(final AppUser appUser);

  Role saveRole(final Role role);

  void addRoleToAppUser(final String email, final String roleName);

  AppUser getAppUser(final String email);

  List<AppUser> getAppUsers();

}
