package com.mas.dashboard.controller;

import com.mas.dashboard.entity.AppUser;
import com.mas.dashboard.entity.Role;
import com.mas.dashboard.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class AppUserController {

  @Autowired
  private AppUserService appUserService;

  @GetMapping("/users")
  public ResponseEntity<List<AppUser>> getAppUsers() {
    return new ResponseEntity<>(this.appUserService.getAppUsers(), HttpStatus.OK);
  }

  @PostMapping("/user/save")
  public ResponseEntity<AppUser> saveAppUser(@RequestBody final AppUser appUser) {
    return new ResponseEntity<>(this.appUserService.saveAppUser(appUser), HttpStatus.CREATED);
  }

  @PostMapping("/role/save")
  public ResponseEntity<Role> saveRole(@RequestBody final Role role) {
    return new ResponseEntity<>(this.appUserService.saveRole(role), HttpStatus.CREATED);
  }

  @PatchMapping("/user/add-role")
  public ResponseEntity<Void> addRoleToUser(@RequestParam("email") final String email,
                                            @RequestParam("roleName") final String roleName) {
    this.appUserService.addRoleToAppUser(email, roleName);
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
