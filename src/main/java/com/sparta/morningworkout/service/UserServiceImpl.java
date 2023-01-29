package com.sparta.morningworkout.service;



import com.sparta.morningworkout.dto.users.LoginUserRequestDto;
import com.sparta.morningworkout.dto.users.SellerRegistRequestDto;
import com.sparta.morningworkout.dto.users.SignupDto;
import com.sparta.morningworkout.entity.*;
import com.sparta.morningworkout.jwtUtil.JwtUtil;
import com.sparta.morningworkout.repository.PointRepository;
import com.sparta.morningworkout.repository.ProfileRepository;
import com.sparta.morningworkout.repository.SellerRegistRepository;
import com.sparta.morningworkout.repository.UserRepository;
import com.sparta.morningworkout.service.serviceInterface.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final SellerRegistRepository sellerRegistRepository;
    private final PointRepository pointRepository;
    private final JwtUtil jwtUtil;
    private final ProfileRepository profileRepository;
    private static final String ADMIN_TOKEN = "asdasd";
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String signup(SignupDto sign) {
        Optional<User> users = userRepository.findByUsername(sign.getUsername());
        if (users.isPresent()) { throw new IllegalArgumentException("유저가 존재합니다"); }
        String username = sign.getUsername();
        String password = passwordEncoder.encode(sign.getPassword());


        UserRoleEnum role = UserRoleEnum.CUSTOMER;
        if (sign.isAdmin()) {
            if (!sign.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("운영자 암호가 틀려 등록이 불가");
            }
            role = UserRoleEnum.ADMIN;
        }
        User user = User.builder()
                        .username(username)
                        .password(password)
                        .role(role)
                        .build();
        userRepository.save(user);
        Profile profile = new Profile(user.getId(),sign.getNickname());
        profileRepository.save(profile);
        Point point = new Point(user.getId(),1000);
        pointRepository.save(point);
        return "회원가입 성공";
    }

    @Override
    public String login(LoginUserRequestDto loginUserRequestDto, HttpServletResponse response) {
        User user = userRepository.findByUsername(loginUserRequestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다"));
        if (!passwordEncoder.matches(loginUserRequestDto.getPassword(),user.getPassword()))
        { throw new IllegalArgumentException("비밀번호 불일치"); }
        addTokenToHeader(response,user);

        return "로그인 성공";
    }

    @Override
    public void logout(User user) {}

    @Override
    public String sellerRegist(SellerRegistRequestDto sellerRegistRequestDto, User user) {
                SellerRegist sellerRegist = new SellerRegist(user.getId(), user.getUsername(),
                        sellerRegistRequestDto.getInfoContent(),sellerRegistRequestDto.getCategory());
                sellerRegistRepository.save(sellerRegist);
        return "판매자 요청 성공";
    }
    private void addTokenToHeader(HttpServletResponse response, User user) {
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    //유저 이름으로 상품을 검색하기 위해 유저 서비스딴에 유저의 이름으로 유저의 아이디를 뱉어내는 함수 추가.
//    public List<Long> getUserIdByName(String username){
//        List<User> users = userRepository.findByUsernameContaining(username);
//
//        if(users == null){
//            throw new IllegalArgumentException("찾으시는 회원의 검색 결과가 없습니다!");
//        }
//
//        List<Long> searchedUserIds = users.stream().map(s -> s.getId()).collect(Collectors.toList());
//
//
//
//
//
//
//        return searchedUserIds;
//    }
}
