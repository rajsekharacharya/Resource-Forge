package com.app.resourceforge.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import com.app.resourceforge.service.serviceImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@SuppressWarnings("deprecation")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final MyAuthenticationMySuccessHandler successHandler;
	private final MyAuthenticationFailureHandler failureHandler;
	private final MyAccessDeniedHandler deniedHandler;

	public WebSecurityConfig(
			MyAuthenticationMySuccessHandler successHandler,
			MyAuthenticationFailureHandler failureHandler,
			MyAccessDeniedHandler deniedHandler) {
		this.successHandler = successHandler;
		this.failureHandler = failureHandler;
		this.deniedHandler = deniedHandler;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public SessionAuthenticationStrategy sessionAuthenticationStrategy(SessionRegistry sessionRegistry) {
		return new RegisterSessionAuthenticationStrategy(sessionRegistry);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/v3/api-docs/**", "/swagger-resources", "/swagger-ui.html", "/getuser", "/forgotpassword",
						"/test/**", "/common/**", "/uploads/**", "/api/system-info/**","/privacy-policy")
				.permitAll()
				.antMatchers("/homeCompany", "/superCompany", "/subscription", "/superadmin").hasAuthority("VARELI")
				.antMatchers("/homeSuperAdmin", "/masterSubscription", "/masterCompany", "/masterUser")
				.hasAuthority("SUPERADMIN")
				.antMatchers("/home", "/assets")
				.hasAnyAuthority("ADMIN", "MANAGER", "PURCHASE", "SERVICE", "FINANCE", "BACKOFFICE", "LOGISTICS",
						"SERVICEENGINEER")
				.antMatchers("/login").not().authenticated()
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.successHandler(successHandler)
				.failureHandler(failureHandler)
				.permitAll()
				.and()
				.httpBasic()
				.and()
				.rememberMe()
				.key("AbcdEfghIjklmNopQrsTuvXyz_0123456789")
				.tokenValiditySeconds(3600)
				.and()
				.logout()
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/")
				.permitAll()
				.and()
				.exceptionHandling()
				.accessDeniedHandler(deniedHandler);

		http.addFilterAfter(afterLoginFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/assets/**", "/private/forgotpassword.js", "/error-subscription-end");
	}

	@Bean
	public AfterLoginFilter afterLoginFilter() throws Exception {
		return new AfterLoginFilter();
	}
}
