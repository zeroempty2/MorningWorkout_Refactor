package com.sparta.morningworkout.redis.refreshToken.entity;

import org.springframework.data.annotation.Id;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;



@Getter
@RedisHash(value = "refreshToken", timeToLive = 60*60*1000)
public class RefreshToken{
  @Id
  private long id;

  private String token;

  private boolean logout;

  public RefreshToken(long id, String token) {
    this.id = id;
    this.token = token;
    this.logout = false;
  }

  public void refreshTokenUpdate(String token){
    this.token = token;
    this.logout = false;
  }
  public void refreshTokenLogout(){
    this.logout = true;
  }
}
