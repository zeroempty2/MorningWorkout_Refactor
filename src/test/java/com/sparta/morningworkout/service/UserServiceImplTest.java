package com.sparta.morningworkout.service;

import com.sparta.morningworkout.config.SecurityConfig;
import com.sparta.morningworkout.dto.users.SignupDto;
import com.sparta.morningworkout.jwtUtil.JwtUtil;
import com.sparta.morningworkout.repository.PointRepository;
import com.sparta.morningworkout.repository.ProfileRepository;
import com.sparta.morningworkout.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private JwtUtil jwtUtil;
    @InjectMocks
    private SecurityConfig securityConfig;

    @Mock
    private PointRepository pointRepository;
    @Spy
    private BCryptPasswordEncoder passwordEncoder;
//    @BeforeEach
//    void prepare() {
//        ReflectionTestUtils.setField(jwtUtil,"secretKey","7ZWt7ZW0OTntmZTsnbTtjIXtlZz");
//        jwtUtil.init();
//    }
    @Test
    @DisplayName("회원 가입")
    void signup() {
        //given
        SignupDto signupDto = SignupDto.builder()
                .username("Lee1231")
                .password("password1")
                .nickname("LEE")
                .build();
        when(userRepository.findByUsername(any(String.class)))
                .thenReturn(Optional.empty());
        //when
        String msg = userService.signup(signupDto);
        //then
        assertThat(msg).isEqualTo("회원가입 성공");
    }

//    @Test
//    @DisplayName("로그인")
//    void login() {
//        //given
//        LoginUserRequestDto loginUserRequestDto = LoginUserRequestDto.builder().username("Lee1231").password("password1").build();
//        User user = User.builder().username("Lee1231").password(passwordEncoder.encode("password1")).role(UserRoleEnum.CUSTOMER).build();
//        MockHttpServletResponse servletResponse = new MockHttpServletResponse();
//        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
//        when(jwtUtil.createToken(user.getUsername(),user.getRole())).thenReturn(any());
//        //when
//        String msg = userService.login(loginUserRequestDto,servletResponse);
//        //then
//        assertThat(msg).isEqualTo("로그인 성공");
//    }
}