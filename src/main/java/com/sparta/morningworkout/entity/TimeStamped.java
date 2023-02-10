package com.sparta.morningworkout.entity;

import com.sparta.morningworkout.security.UserDetailsImpl;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@SuperBuilder
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeStamped {

  @CreatedDate
  private LocalDateTime createdAt;
  @LastModifiedDate
  private LocalDateTime modifiedAt;
  @CreatedBy
  @ManyToOne
  private User createdBy;
  @LastModifiedBy
  @ManyToOne
  private User modifiedBy;

  public void updateCreatedBy() {
    var user = getCurrentAuditor();
    user.ifPresent(value -> this.createdBy = value);
  }

  public void updateModifiedBy() {
    var user = getCurrentAuditor();
    user.ifPresent(value -> this.modifiedBy = value);
  }

  private Optional<User> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty();
    }

    return Optional.of(((UserDetailsImpl) authentication.getPrincipal()).getUser());
  }
}

