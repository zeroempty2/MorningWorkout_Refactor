package com.sparta.morningworkout.redis.refreshToken.controller;

import com.sparta.morningworkout.dto.ResponseRefreshToken;
import com.sparta.morningworkout.jwtUtil.JwtUtil;
import com.sparta.morningworkout.redis.refreshToken.dto.RefreshRequest;
import com.sparta.morningworkout.redis.refreshToken.dto.TokenAndUser;
import com.sparta.morningworkout.redis.refreshToken.sevice.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refresh")
public class RefreshTokenController {
  private final RefreshTokenService refreshTokenService;
  private final JwtUtil jwtUtil;
  @PostMapping("/reissuance")
  public ResponseEntity<ResponseRefreshToken> reissuanceToken(@RequestBody RefreshRequest refreshRequest, HttpServletResponse response){
    TokenAndUser tokenAndUser = refreshTokenService.reissuanceToken(refreshRequest);
    response.setHeader(JwtUtil.AUTHORIZATION_HEADER,jwtUtil.createToken(tokenAndUser.getUsername(),tokenAndUser.getUserRoleEnum()));
    ResponseRefreshToken refreshToken = ResponseRefreshToken.builder().statusCode(200).msg("재발급 완료").refreshToken(tokenAndUser.getToken()).build();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
    return ResponseEntity.ok().headers(headers).body(refreshToken);
  }
}
