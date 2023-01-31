package com.sparta.morningworkout.redis.refreshToken.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RefreshRequest {
  private final String refreshToken;

}
