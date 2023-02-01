package com.app.coffee.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.coffee.entity.Privilege;
import com.app.coffee.entity.Role;
import com.app.coffee.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

	private UUID id;

	private String email;

	@JsonIgnore
	private String password;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    public UUID getId(){
        return this.id;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public static CustomUserDetails build(User user) {
		return CustomUserDetails.builder()
        .id(user.getId())
        .email(user.getEmail())
        .password(user.getPassword())
        .authorities(getAuthorities(user.getRoles()))
        .build();
	}
    private static Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
            final List<GrantedAuthority> authorities = new ArrayList<>();
            final List<String> privileges = new ArrayList<>();
            final List<Privilege> collection = new ArrayList<>();
            for (final Role role : roles) {
                privileges.add(role.getName());
                collection.addAll(role.getPrivileges());
            }
            for (final Privilege item : collection) {
                privileges.add(item.getName());
            }
            for (final String privilege : privileges) {
                authorities.add(new SimpleGrantedAuthority(privilege));
            }
            return authorities; 
    }
  

   
}