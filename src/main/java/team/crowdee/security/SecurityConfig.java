package team.crowdee.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import team.crowdee.jwt.JwtAccessDeniedHandler;
import team.crowdee.jwt.JwtAuthenticationEntryPoint;
import team.crowdee.jwt.JwtSecurityConfig;
import team.crowdee.jwt.TokenProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 메소드 단위로 @PreAuthorize 검증 어노테이션을 사용하기 위해 추가
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);

        // JWT security 추가
        web
                .ignoring()
                .antMatchers(
                        "/h2-console/**",
                        "/favicon.ico"
                );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                // test
//                .authorizeRequests()
//                .antMatchers("/api/hello").permitAll()
//                .anyRequest().authenticated();


                .httpBasic().disable()
                .cors().disable()
                .csrf().disable()

                // JWT 예외처리를 위한 코드 지정
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 데이터 확인을 위해 사용하고 있는 h2-console을 위한 설정 추가
                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // 세션을 사용하지 않기 위해 세션설정 -> STATELESS
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // login, signup (나중에 수정할 것) 3가지 API는 토큰없이 호출할 수 있도록 허용
                // 메인화면, 로그인창 등은 token 없이도 접근이 가능해야 하니까....
                .and()
                .authorizeRequests()
                .antMatchers("/member1/login").permitAll() //로그인 api
                .antMatchers("/member1/signUp").permitAll() //회원가입 api

                .anyRequest().permitAll()

                // JwtFilter 를 addFilterBefore 메소드로 등록했던 JwtSecurityConfig 클래스도 적용
                .and()
                .apply(new JwtSecurityConfig(tokenProvider));


    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //cors 설정
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    // JWT security 추가 로직
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(TokenProvider tokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }
}
