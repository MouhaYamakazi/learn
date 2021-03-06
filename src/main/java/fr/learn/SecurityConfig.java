package fr.learn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import fr.learn.member.MemberDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired 
	private MemberDetailsService memberDetailsService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.csrf().disable();
		 http
         .authorizeRequests()
             .antMatchers("/resources/**", "/register.html", 
            		 "/index.html", "/style/**", "/images/**","/navbar.html",
            		 "/js/**").permitAll()
             .anyRequest().authenticated()
             .and()
         .formLogin()
             .loginPage("/login.html")
             .permitAll()
             .and()
         .logout()
             .permitAll()
             .logoutSuccessUrl("/index.html")
             .and();
	}
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
	
	
}
