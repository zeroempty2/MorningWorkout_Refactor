package com.sparta.morningworkout.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.morningworkout.dto.users.LoginUserRequestDto;
import com.sparta.morningworkout.dto.users.SellerRegistRequestDto;
import com.sparta.morningworkout.dto.users.SignupDto;
import com.sparta.morningworkout.entity.CategoryEnum;
import com.sparta.morningworkout.entity.User;
import com.sparta.morningworkout.security.UserDetailsImpl;
import com.sparta.morningworkout.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static com.sparta.morningworkout.controller.ApiDocumentUtils.getDocumentRequest;
import static com.sparta.morningworkout.controller.ApiDocumentUtils.getDocumentResponsee;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@WebMvcTest(controllers = UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserServiceImpl userService;

    @Test
    @WithMockUser(username = "username1", roles = "CUSTOMER")
    void createUser() throws Exception {

        SignupDto requestDto = SignupDto.builder()
                .username("username1")
                .password("password11**")
                .admin(false)
                .nickname("nickname1")
                .adminToken("")
                .build();

        ResultActions resultActions = mockMvc.perform(post("/users/sign")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isCreated());

        resultActions.andDo(document("userController/sign",
                getDocumentRequest(),
                getDocumentResponsee(),
//
                requestFields(
                        fieldWithPath("username").type(JsonFieldType.STRING).description("회원_이름"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원_비밀번호"),
                        fieldWithPath("admin").type(JsonFieldType.BOOLEAN).description("회원_자격"),
                        fieldWithPath("adminToken").type(JsonFieldType.STRING).description("관리자_토큰"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("회원_닉네임")
                ),
                responseFields(
                        fieldWithPath("httpStatusCode").type(JsonFieldType.STRING).description("결과코드"),
                        fieldWithPath("msg").type(JsonFieldType.NULL).description("결과메시지")
                )
        ));
    }

    @Test
    @WithMockUser(username = "username1", roles = "CUSTOMER")
    void login() throws Exception {

        LoginUserRequestDto requestDto = LoginUserRequestDto.builder()
                .username("username1")
                .password("password11**")
                .build();

        ResultActions resultActions = mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
        resultActions.andDo(document("userController/login",
                getDocumentRequest(),
                getDocumentResponsee(),
//
                requestFields(
                        fieldWithPath("username").type(JsonFieldType.STRING).description("회원_이름"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("회원_비밀번호")
                ),
                responseFields(
                        fieldWithPath("httpStatusCode").type(JsonFieldType.STRING).description("결과코드"),
                        fieldWithPath("msg").type(JsonFieldType.NULL).description("결과메시지")
                )
        ));
    }

    @Test
    @WithMockUser(username = "username1", roles = "CUSTOMER")
    void logout() throws Exception {
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        User user = User.builder()
                .username("username1")
                .password("password11!!")
                .build();
        given(userDetails.getUser()).willReturn(user);
        ResultActions resultActions = mockMvc.perform(post("/users/logout")
                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(requestDto))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());
        resultActions.andDo(document("userController/logout",
                getDocumentRequest(),
                getDocumentResponsee(),
                responseFields(
                        fieldWithPath("httpStatusCode").type(JsonFieldType.STRING).description("결과코드"),
                        fieldWithPath("msg").type(JsonFieldType.STRING).description("결과메시지")
                )
        ));

    }

//    @Test
//    @WithUserDetails
//    void athorization() throws Exception {
//
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        User user = User.builder()
//                .username("username1")
//                .password("password11!!")
//                .build();
//        given(userDetails.getUser()).willReturn(user);
//
//        SellerRegistRequestDto requestDto = SellerRegistRequestDto.builder()
//                .infoContent("상품정보")
//                .category(CategoryEnum.IT)
//                .build();
//
//        ResultActions resultActions = mockMvc.perform(post("/users/authorization")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsBytes(requestDto))
//                        .content(objectMapper.writeValueAsString(userDetails))
//                        .accept(MediaType.APPLICATION_JSON)
//                        .with(csrf()))
//                .andExpect(status().isOk());
//
//        resultActions.andDo(document("userController/authorization",
//                getDocumentRequest(),
//                getDocumentResponsee(),
//                requestFields(
//                        fieldWithPath("infoContent").type(JsonFieldType.STRING).description("상품_정보"),
//                        fieldWithPath("category").type(JsonFieldType.OBJECT).description("카테고리_내역")
//                ),
//                responseFields(
//                        fieldWithPath("httpStatusCode").type(JsonFieldType.STRING).description("결과코드"),
//                        fieldWithPath("msg").type(JsonFieldType.STRING).description("결과메시지")
//                )
//        ));
//
//    }
}
