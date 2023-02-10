//package com.sparta.morningworkout.service;
//
//import com.sparta.morningworkout.dto.StatusResponseDto;
//import com.sparta.morningworkout.entity.Profile;
//import com.sparta.morningworkout.entity.SellerRegist;
//import com.sparta.morningworkout.entity.User;
//import com.sparta.morningworkout.entity.UserRoleEnum;
//import com.sparta.morningworkout.repository.ProfileRepository;
//import com.sparta.morningworkout.repository.SellerRegistRepository;
//import com.sparta.morningworkout.repository.UserRepository;
//import java.util.Objects;
//import java.util.Optional;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class AdminServiceImplTest {
//    @InjectMocks
//    AdminServiceImpl adminService;
//    @Mock
//    UserRepository userRepository;
//    @Mock
//    SellerRegistRepository sellerRegistRepository;
//    @Mock
//    ProfileRepository profileRepository;
//
//    @Test
//    void showCustomerList() {
//    }
//
//    @Test
//    void showCustomerListBySearchingNickname() {
//    }
//
//    @Test
//    void acceptSellerRegist() {
//        SellerRegist sellerRegist = mock(SellerRegist.class);
//        Profile profile = mock(Profile.class);
//
//        User user = mock(User.class);
//
//
//        when(sellerRegistRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(sellerRegist));
//        when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(user));
//        when(profileRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(profile));
//        //when
//        StatusResponseDto statusResponseDto = adminService.acceptSellerRegist(any(Long.class));
//        verify(user).changeSeller();
//        verify(profile).authorizationProfileUpdate(Objects.requireNonNull(sellerRegist));
//        StatusResponseDto statusResponseDto1 = new StatusResponseDto(200, "판매자 승인이 완료되었습니다");
//
//
//        Assertions.assertThat(statusResponseDto.getMessage()).isEqualTo(statusResponseDto1.getMessage());
//        Assertions.assertThat(statusResponseDto.getStatusCode()).isEqualTo(statusResponseDto1.getStatusCode());
//    }
//}
