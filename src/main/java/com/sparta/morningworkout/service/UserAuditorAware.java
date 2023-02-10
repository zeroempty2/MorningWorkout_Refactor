package com.sparta.morningworkout.service;

import com.sparta.morningworkout.entity.User;
import com.sparta.morningworkout.security.UserDetailsImpl;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAuditorAware implements AuditorAware<User> {
  @Override
  public Optional<User> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty();
    }

    return Optional.of(((UserDetailsImpl) authentication.getPrincipal()).getUser());
  }
}
