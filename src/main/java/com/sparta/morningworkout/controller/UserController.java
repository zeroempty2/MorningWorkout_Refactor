package com.sparta.morningworkout.controller;

import com.sparta.morningworkout.dto.ResponseRefreshToken;
import com.sparta.morningworkout.dto.RestApiResponse;
import com.sparta.morningworkout.dto.users.LoginUserRequestDto;
import com.sparta.morningworkout.dto.users.SellerRegistRequestDto;
import com.sparta.morningworkout.dto.users.SignupDto;
import com.sparta.morningworkout.entity.User;
import com.sparta.morningworkout.jwtUtil.JwtUtil;
import com.sparta.morningworkout.security.UserDetailsImpl;
import com.sparta.morningworkout.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServiceImpl userServiceimpl;

    @PostMapping("/sign")
    public ResponseEntity createUser(@RequestBody @Valid SignupDto sign) {
        String msg = userServiceimpl.signup(sign);
        RestApiResponse restApiResponse = new RestApiResponse(HttpStatus.CREATED, msg);
        return ResponseEntity.status(HttpStatus.CREATED).body(restApiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginUserRequestDto loginUserRequestDto, HttpServletResponse response) {
        ResponseRefreshToken refreshToken = userServiceimpl.login(loginUserRequestDto, response);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return ResponseEntity.ok().headers(headers).body(refreshToken);
    }

    @Transactional
    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletResponse response, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        userServiceimpl.logout(userDetails.getUserId());
        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, null);
        String msg = "로그아웃 성공";
        RestApiResponse restApiResponse = new RestApiResponse(HttpStatus.OK, msg);

        return ResponseEntity.status(HttpStatus.OK).body(restApiResponse);
    }

    @PostMapping("/authorization")
    public ResponseEntity athorization(@RequestBody SellerRegistRequestDto sellerRegistRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String msg = userServiceimpl.sellerRegist(sellerRegistRequestDto, user);
        RestApiResponse restApiResponse = new RestApiResponse(HttpStatus.OK, msg);

        return ResponseEntity.status(HttpStatus.OK).body(restApiResponse);
    }
}
