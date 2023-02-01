package com.app.coffee.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorAwareImpl implements AuditorAware<String> {
  
    @Override
    public Optional<String> getCurrentAuditor() {
            System.out.println(Optional.ofNullable(SecurityContextHolder.getContext())
            .map(e -> e.getAuthentication())
            .map(Authentication::getName));
        return Optional.ofNullable(SecurityContextHolder.getContext())
            .map(e -> e.getAuthentication())
            .map(Authentication::getName);
    }
}
