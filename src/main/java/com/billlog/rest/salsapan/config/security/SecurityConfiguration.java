package com.billlog.rest.salsapan.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable() // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable() // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/*/info/**","/v1/user/emailConfirm","/salsalpan_privacy.html").permitAll() // 가입 및 인증 주소는 누구나 접근가능
                .antMatchers("/web/**","/css/**", "/js/**", "/vendors/**", "/node_modules/**", "/images/**").permitAll() // 정적파일 및 웹 관련은 누구나 접근가능 (아직은)
                .antMatchers("/*/signin", "/*/signin/**", "/*/signup", "/*/signup/**", "/*/social/**").permitAll() // 가입 및 인증 주소는 누구나 접근가능
                .antMatchers("/*/common/**").permitAll() // 공통 관련은 누구나 접근가능
                .antMatchers("/v1/fcm/device").permitAll() // 공통 관련은 누구나 접근가능
                .antMatchers("/v1/file/downloadFile/**").permitAll() // 이미지 내려받기는 누구나 접근가능
                .antMatchers(HttpMethod.GET, "/exception/**", "helloworld/**").permitAll() // hellowworld로 시작하는 GET요청 리소스는 누구나 접근가능
                .antMatchers("/v1/fcm/sendAll").hasRole("ADMIN") // 공통 관련은 누구나 접근가능
                .antMatchers("/v1/manage/**").hasRole("ADMIN") // 공통 관련은 누구나 접근가능
                .anyRequest().authenticated() // 인증된 사용자는 접근해라.
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣는다

    }

    @Override // ignore check swagger resource
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**");

    }
}