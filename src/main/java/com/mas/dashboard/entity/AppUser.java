package com.mas.dashboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "app_user")
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String first_name;

  private String last_name;

  private String username;

  private String email;

  private String password;

  private Boolean deleted;

  @ManyToMany(fetch = FetchType.EAGER)
  private Collection<Role> role = new ArrayList<>();
}
