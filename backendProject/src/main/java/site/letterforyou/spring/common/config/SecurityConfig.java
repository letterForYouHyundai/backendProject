package site.letterforyou.spring.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.headers().frameOptions().sameOrigin().and()
			.httpBasic().disable()
		//	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		//	.and()
			.authorizeRequests()
			/* ROLE을 사용하는 경우만 처리
			.antMatchers("/admin/**").hasRole("ADMIN") 
			*/		
//			.antMatchers("/**").permitAll()
			.antMatchers("/").authenticated()
			.anyRequest().permitAll()
			//.and().formLogin().loginPage("/login/loginView")
			//.and().logout().logoutUrl("/login/logout").logoutSuccessUrl("/login/loginView").deleteCookies("boaccessToken")
			.and().csrf().disable();
			//.addFilterBefore(new LoginAuthenticationFilter(jwtTokenProvider,accountService, redisUtil, initConfig), UsernamePasswordAuthenticationFilter.class);
	}
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/css/**", "/js/**", "/fonts/**");
	}
	
	
}
