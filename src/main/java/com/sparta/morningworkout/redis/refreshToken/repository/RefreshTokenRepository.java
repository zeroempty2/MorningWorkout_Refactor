package com.sparta.morningworkout.redis.refreshToken.repository;

import com.sparta.morningworkout.redis.refreshToken.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {

}
