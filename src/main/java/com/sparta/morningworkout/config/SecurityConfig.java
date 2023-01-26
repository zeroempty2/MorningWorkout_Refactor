package com.sparta.morningworkout.config;



import com.sparta.morningworkout.jwtUtil.JwtUtil;
import com.sparta.morningworkout.security.CustomAccessDeniedHandler;
import com.sparta.morningworkout.security.CustomAuthenticationEntryPoint;
import com.sparta.morningworkout.security.JwtAuthFilter;
import com.sparta.morningworkout.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .requestMatchers("/users/sign")
                .requestMatchers("/docs/**")
                .requestMatchers("/users/logout");

    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeHttpRequests()
                .requestMatchers("/products/seller/**").hasAnyRole("SELLER")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                 // Enum형태로 넣으면 인식 못함!
                .requestMatchers("/products/list").permitAll()
                .requestMatchers("/products/search/**").permitAll()
                .requestMatchers("/products/list/seller/**").permitAll()
                .requestMatchers("/point/**").permitAll()
                .requestMatchers("/point/admin").hasAnyRole("ADMIN")
                .requestMatchers("/chat/**").permitAll()
                .requestMatchers("/chatroom/**").permitAll()
                .requestMatchers("/customers/**").hasAnyRole("CUSTOMER")
                .requestMatchers("/profile/**").permitAll()
                .requestMatchers("/users/login").permitAll()
                .requestMatchers("/sellers/**").hasAnyRole("SELLER")
                .requestMatchers("/users/authorization").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class);

        //401 인증과정 실패시 에러처리
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        //403
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);

        return http.build();
    }
}
