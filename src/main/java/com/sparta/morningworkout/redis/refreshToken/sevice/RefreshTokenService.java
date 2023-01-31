package com.sparta.morningworkout.redis.refreshToken.sevice;


import com.sparta.morningworkout.entity.User;
import com.sparta.morningworkout.redis.refreshToken.dto.RefreshRequest;
import com.sparta.morningworkout.redis.refreshToken.dto.TokenAndUser;
import com.sparta.morningworkout.redis.refreshToken.entity.RefreshToken;
import com.sparta.morningworkout.redis.refreshToken.repository.RefreshTokenRepository;
import com.sparta.morningworkout.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;
  @Transactional
  public String addRefreshToken(long userId){
    String token = userId+UUID.randomUUID().toString();
    RefreshToken refresh = new RefreshToken(userId,token);
    Optional<RefreshToken> refreshToken = refreshTokenRepository.findById(refresh.getId());
    if(refreshToken.isPresent()){
      refreshToken.get().refreshTokenUpdate(token);
      return token;
    }
    refreshTokenRepository.save(refresh);
    return token;
    }
    public void RefreshTokenLogout(long userId){
      RefreshToken refreshToken = getRefreshTokenById(userId);
      refreshToken.refreshTokenLogout();
    }
    public TokenAndUser reissuanceToken(RefreshRequest refreshRequest){
    try {
      long userId = Long.parseLong(refreshRequest.getRefreshToken().substring(1));
      String token = userId+UUID.randomUUID().toString();
      RefreshToken refreshToken = getRefreshTokenById(userId);
      if(refreshToken.isLogout()){
        throw new IllegalArgumentException("유효하지 않은 토큰입니다");
      }
      User user = userRepository.findById(userId).orElseThrow(
          () -> new IllegalArgumentException("유효하지 않은 유저입니다")
      );
      return TokenAndUser.builder().username(user.getUsername()).userRoleEnum(user.getRole()).token(token).build();
    }
    catch (Exception e){
      throw new IllegalArgumentException("유효하지 않은 토큰입니다");
    }

    }
    public RefreshToken getRefreshTokenById(long id){
      return refreshTokenRepository.findById(id).orElseThrow(
          () -> new IllegalArgumentException("유효하지 않은 토큰입니다.")
      );
    }

  }

