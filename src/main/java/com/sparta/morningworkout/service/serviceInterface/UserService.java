package com.sparta.morningworkout.service.serviceInterface;

import com.sparta.morningworkout.dto.ResponseRefreshToken;
import com.sparta.morningworkout.dto.users.LoginUserRequestDto;
import com.sparta.morningworkout.dto.users.SellerRegistRequestDto;
import com.sparta.morningworkout.dto.users.SignupDto;
import com.sparta.morningworkout.entity.User;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
    String signup(SignupDto signupDto);
    ResponseRefreshToken login(LoginUserRequestDto loginUserRequestDto, HttpServletResponse response);
    void logout(long userId);
    String sellerRegist(SellerRegistRequestDto sellerRegistRequestDto, User user);

//    List<Long> getUserIdByName(String username);
}
