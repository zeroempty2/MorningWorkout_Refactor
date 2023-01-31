package com.sparta.morningworkout.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseRefreshToken {
  private int statusCode;
  private String msg;
  private String refreshToken;

  public ResponseRefreshToken(int statusCode, String msg, String refreshToken) {
    this.statusCode = statusCode;
    this.msg = msg;
    this.refreshToken = refreshToken;
  }
}
