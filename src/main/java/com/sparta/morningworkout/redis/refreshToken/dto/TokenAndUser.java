package com.sparta.morningworkout.redis.refreshToken.dto;

import com.sparta.morningworkout.entity.UserRoleEnum;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenAndUser {
  private String username;
  private UserRoleEnum userRoleEnum;
  private String token;
}
