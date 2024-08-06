package com.expohack.slm.authentication.model.entity;

import com.expohack.slm.model.Company;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Пользователь
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "app_user")
public class User implements Serializable, UserDetails {

  @Serial
  private static final long serialVersionUID = 3626860614297922955L;

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "company_id", referencedColumnName = "id")
  private Company company;

  @Column(name = "username", nullable = false)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "account_non_expired", nullable = false)
  private boolean accountNonExpired;

  @Column(name = "account_non_locked", nullable = false)
  private boolean accountNonLocked;

  @Column(name = "credentials_non_expired", nullable = false)
  private boolean credentialsNonExpired;

  @Column(name = "enabled", nullable = false)
  private boolean enabled;

  @Column(name = "admin", nullable = false)
  private boolean admin;

  @Column(name = "manager", nullable = false)
  private boolean manager;

  @Column(name = "employee", nullable = false)
  private boolean employee;

  @Transient
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    val roles = new LinkedHashSet<GrantedAuthority>(8, 0.75f);
    if (this.admin) {
      roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
    if (this.manager) {
      roles.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
    }
    if (this.employee) {
      roles.add(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    }
    return roles;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return this.accountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return this.accountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return this.credentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }
}
