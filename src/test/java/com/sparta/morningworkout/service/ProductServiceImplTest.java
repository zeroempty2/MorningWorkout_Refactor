//package com.sparta.morningworkout.service;
//
//import com.sparta.morningworkout.dto.PageDto;
//import com.sparta.morningworkout.dto.product.ProductRequestDto;
//import com.sparta.morningworkout.dto.product.ProductResponseDto;
//import com.sparta.morningworkout.dto.product.ProductUpdateRequestDto;
//import com.sparta.morningworkout.entity.CategoryEnum;
//import com.sparta.morningworkout.entity.Product;
//import com.sparta.morningworkout.entity.User;
//import com.sparta.morningworkout.repository.ProductRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.*;
//
//
//@ExtendWith(MockitoExtension.class)
//class ProductServiceImplTest {
//
//    @Mock
//    ProductRepository productRepository;
//
//    @InjectMocks
//    ProductServiceImpl productService;
//
//
//    @Test
//    @DisplayName("상품 추가 테스트")
//    void addProduct() {
//        //given
//        ProductRequestDto productRequestDto = mock(ProductRequestDto.class);
//        User user = mock(User.class);
//        productRequestDto = ProductRequestDto.builder()
//                .productName("물건1")
//                .price(20000)
//                .categoryEnum(CategoryEnum.IT)
//                .build();
//
//        //when
//        String result = productService.addProduct(productRequestDto,user);
//        verify(productRepository, times(1)).save(isA(Product.class));
//
//        //then
//        Assertions.assertSame("해당 상품을 등록 완료했습니다", result);
//
//    }
//
//    @Test
//    @DisplayName("상품 내용 업데이트 성공")
//    void updateProduct() {
//        //given
//        Long productId = 1L;
//        ProductUpdateRequestDto productUpdateRequestDto = mock(ProductUpdateRequestDto.class);
//        User user = mock(User.class);
//        Product product = mock(Product.class);
//        given(productRepository.findById(productId)).willReturn(Optional.ofNullable(product));
//        given(user.checkUser(product.getUserId())).willReturn(true);
//        //when
//        String s = productService.updateProduct(productId, productUpdateRequestDto, user);
//        //then
//        Assertions.assertSame("해당 제품의 가격이 업데이트 되었습니다", s);
//    }
//
//    @Test
//    @DisplayName("상품 내용 업데이트 실패")
//    void Fail_updateProduct() {
//        //given
//        Long productId = 1L;
//        ProductUpdateRequestDto productUpdateRequestDto = mock(ProductUpdateRequestDto.class);
//        User user = mock(User.class);
//        Product product = mock(Product.class);
//        given(productRepository.findById(productId)).willReturn(Optional.ofNullable(product));
//        given(user.checkUser(product.getUserId())).willReturn(false);
//        //when
//        //then
//        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(productId, productUpdateRequestDto, user));
//    }
//
//    @Test
//    @DisplayName("상품 삭제")
//    void deleteProduct() {
//        //given
//        Long productId = 1L;
//        ProductUpdateRequestDto productUpdateRequestDto = mock(ProductUpdateRequestDto.class);
//        User user = mock(User.class);
//        Product product = mock(Product.class);
//        given(user.checkUser(any())).willReturn(true);
//        given(productRepository.findById(productId)).willReturn(Optional.ofNullable(product));
////        given(productRepository.delete(isA(Product.class));
//        //when
//        String s = productService.deleteProduct(productId, user);
////        verify(productRepository,only()).delete(any());
//        //then
//        Assertions.assertSame("삭제완료",s);
//    }
//
//    @Test
//    @DisplayName("상품 삭제 실패")
//    void Fail_deleteProduct() {
//        //given
//        Long productId = 1L;
//        ProductUpdateRequestDto productUpdateRequestDto = mock(ProductUpdateRequestDto.class);
//        User user = mock(User.class);
//        Product product = mock(Product.class);
//        given(user.checkUser(any())).willReturn(false);
//        given(productRepository.findById(productId)).willReturn(Optional.ofNullable(product));
//        //when
//        //then
//        Assertions.assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(productId, user));
//    }
//
//    @Test
//    void showProductList() {
//        PageDto pageDto = mock(PageDto.class);
//        Pageable pageable = mock(Pageable.class);
//        pageDto = PageDto.builder().page(1).size(1).build();
////        given(productService.makePage(pageDto)).willReturn(pageable);
//        given(productRepository.findAll(pageable)).willReturn(any());
//        Page<ProductResponseDto> productResponseDtos = productService.showProductList(pageDto);
//        verify(productRepository, only()).findAll(pageable);
//
//    }
//
//    @Test
//    void showProductBySeller() {
//    }
//}
