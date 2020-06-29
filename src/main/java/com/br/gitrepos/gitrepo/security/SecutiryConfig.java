package com.br.gitrepos.gitrepo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecutiryConfig extends WebSecurityConfigurerAdapter{
    
    @Autowired
	private GithubRepoUserDetailsService userDetailsService;

	@Autowired
	private SecurityFilter securityFilter;

    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    @Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
    
    @Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().cors().and().authorizeRequests()
		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll().and().
			authorizeRequests().antMatchers("/search/**").permitAll().and().
			authorizeRequests().antMatchers("/auth/**").permitAll().and().
			authorizeRequests().antMatchers("/h2-console/**").permitAll(). //liberado apenas p/ o caso de se querer avaliar o banco
			anyRequest().authenticated().and().exceptionHandling().and().
			addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class).sessionManagement().
			sessionCreationPolicy(SessionCreationPolicy.STATELESS);

			httpSecurity.headers().frameOptions().disable();
	}

	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
								   "/configuration/ui",
								   "/swagger-resources/configuration/ui",
                                   "/swagger-resources/**",
                                   "/configuration/security",
								   "/swagger-ui.html",
								   "/swagger-resources/**",
								   "/webjars/**",
								   "/**.html");
	}
	
}