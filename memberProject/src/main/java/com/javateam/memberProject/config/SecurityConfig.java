package com.javateam.memberProject.config;


import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.javateam.memberProject.service.CustomProvider;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Slf4j
public class SecurityConfig {

	@Autowired
	private CustomProvider customProvider;	
	
	private UserDetailsService userDetailsService;

	private DataSource dataSource;

	public SecurityConfig(UserDetailsService userDetailsService, DataSource dataSource) {

		log.info("생성자 주입");
		this.dataSource = dataSource;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    // security 적용 예외 URL 등록
	// 10.13 : /bootstrap-icons/** 추가
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
    	
    	
    	// 10.17 : "/bootstrap/**" 추가, "/" 제외 : 정확한 인증/인가 적용을 위한 조치    	
    	// 10.18 : "/summernote/**" 추가
    	// swagger 항목 예외(열외) 추가 : 
    	// 참고) /v2/api-docs : swagger의 전체적인 환경설정 정보를 JSON 형식으로 보여주는 페이지
    	// /v2/api-docs, /swagger-resources/**, /swagger/**, swagger-ui.html
    	// axios 항목 예외 추가
    	return (web) -> web.ignoring().antMatchers("/css/**", "/webjars/**", 
    				"/images/**", "/js/**", "/v2/api-docs", "/swagger-resources/**", "/swagger/**", "/swagger-ui.html",
    				"/axios/**", "/bootstrap-icons/**", "/bootstrap/**", "/summernote/**");    	
    }

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	http
            .csrf().disable()
            .userDetailsService(userDetailsService)
            .authenticationProvider(customProvider)
            .headers().frameOptions().disable()
            .and()
                .authorizeRequests() 
                // axios 추가
                // 10.17 : security 적용 예외 URL 등록와의 중복 부분 제외 => "/"만 적용
                // 10.13 : /bootstrap-icons/** 추가
                // .antMatchers("/", "/css/**", "/webjars/**", "/images/**", "/js/**", "/axios/**", "/bootstrap-icons/**").permitAll()
                .antMatchers("/").permitAll() // 10.17
                // 추가 (swagger)
                .antMatchers("/swagger-resources/**", "/swagger/**", "/swagger-ui.html").permitAll()
                // 추가 : 10.6 : /member/hasFldForUpdate/**
                .antMatchers("/member/hasFld/**", "/member/hasFldForUpdate/**").permitAll()
                // 추가 : 10.4 : /member/view.do (임시 조치)
                // .antMatchers("/member/hasFld/**", "/member/view.do").permitAll()
                // 변경 : 10.4 : /member/view.do (조치)
                .antMatchers("/member/view.do").authenticated()
                // .antMatchers("/member/view.do").hasAnyRole("USER", "ADMIN")
                // 추가 : 10.5 : /member/update.do, /member/updateProc.do
                .antMatchers("/member/update.do", "/member/updateProc.do").authenticated()
                // 추가 : 10.10 : /member/updateSess.do", "/member/updateSessProc.do
                .antMatchers("/member/updateSess.do", "/member/updateSessProc.do").authenticated()
                .antMatchers("/member/join.do", "/member/joinProc.do", "/member/joinProcRest.do").permitAll()
                // 추가 : 10.12 : /member/updateRoles/**
                // 추가 : 10.13 : /member/changeMemberState/**
                // 추가 : 10.16 : /member/updateMemberByAdmin/**, /member/deleteMemberByAdmin/**
                .antMatchers("/member/updateRoles/**", "/member/changeMemberState/**", 
                			 "/member/updateMemberByAdmin/**", "/member/deleteMemberByAdmin/**").authenticated()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                // 추가 : 10.17 : "/content1", "/content2"
                .antMatchers("/content1", "/content2").permitAll()	
                // 추가 : 10.18 : "/board/write.do", "/board/writeProc.do"
                .antMatchers("/board/write.do","/board/writeProc.do").authenticated()
                // 추가 : 10.19 : 게시글 summernote 이미지 업로드 관련 URL ; "/board/image", "/board/image/**"
                // 추가 : 10.23 : 게시글 목록 ; "/board/list.do", "/board/view.do"
                // 추가 : 10.24 : 게시글 검색 목록 ; "/board/searchList.do"
                // 추가 : 10.25 : 게시글 수정 ; "/board/update.do", "/board/updateProc.do"
                // 추가 : 10.26 : 댓글 작성 ; "/board/replyWrite.do"  
                // 추가 : 10-26 (2) : 전체 댓글 조회 ; "/board/getRepliesAll.do" 
                // 추가 : 10-27 : 댓글 수정 ; "/board/replyUpdate.do"
                // 추가 : 10-30 : 댓글 삭제 ; "/board/replyDelete.do"
                // 추가 : 10-30 (2) : 게시글 삭제 ; "/board/deleteProc.do"
                .antMatchers("/board/image", "/board/image/**", "/board/list.do", 
                			 "/board/view.do", "/board/searchList.do",
                			 "/board/update.do", "/board/updateProc.do",
                			 "/board/replyWrite.do", "/board/replyUpdate.do", 
                			 "/board/getRepliesAll.do", "/board/replyDelete.do",
                			 "/board/deleteProc.do").authenticated()
                .anyRequest().authenticated()
            .and()    
	            .formLogin()
	                .loginPage("/login")
	                .usernameParameter("username")
	    			.passwordParameter("password")
	    			.defaultSuccessUrl("/welcome")                
	                .failureUrl("/loginError")
	                //.successHandler(new CustomAuthenticationSuccess()) // 로그인 성공 핸들러 
	                //.failureHandler(new CustomAuthenticationFailure()) // 로그인 실패 핸들러 
	                .permitAll()
            .and()
            	.logout().permitAll()
            .and()
               .exceptionHandling().accessDeniedPage("/403");    		 
//            .and()
//                .logout()
//                    .logoutSuccessUrl("/")
                    
//            .and()
//                .oauth2Login()
//                    .userInfoEndpoint()
//                        .userService(customOAuth2UserService);
    	
    	return http.build();
    } //
    

	// 추가된 remember-me 관련 메서드
	private PersistentTokenRepository getJDBCRepository() {

		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);

		return repo;
	} //
}
