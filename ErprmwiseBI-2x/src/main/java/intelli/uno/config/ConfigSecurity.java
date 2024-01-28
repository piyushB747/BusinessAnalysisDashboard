package intelli.uno.config;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;

import intelli.uno.service.entityengineermaster.ServiceEntityEngineerMaster;

import java.util.Arrays;
@Configuration
@EnableWebSecurity
public class ConfigSecurity {
	

	@Autowired
	private ServiceEntityEngineerMaster serviceEntityEngineerMaster;

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


    @Bean
    DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(serviceEntityEngineerMaster);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .cors(cors -> cors
                .configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(Arrays.asList("http://localhost:8084")); // Add your allowed origin(s)
                    config.setAllowedMethods(Arrays.asList("*")); // Allow all methods (GET, POST, etc.)
                    config.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
                    return config;
                })
            ) 
                .authorizeHttpRequests()
                .antMatchers("/**","/registration**", "/js/**", "/css/**", "/img/**", "/resources/**", "/images/**","/BI_images/**","/CustomCssJs/**")
                .permitAll()
                .antMatchers("/passwordupdate")
                .permitAll()
                .antMatchers("/executivehomes")
                .permitAll()
                .antMatchers("/error")
                .permitAll()
                .antMatchers("/apitoslaviolated/regionwise")
                .permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement(management -> management
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .invalidSessionUrl("/login?timeout")
                        .maximumSessions(2)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/login?expired")
                        .and()
                        .sessionFixation().migrateSession())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/successhome", true)
                        .successHandler((request, response, authentication) -> {

                            String contextPath = request.getContextPath();
               
                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
               
                            if (authorities.stream().anyMatch(a -> a.getAuthority().equals("SD")) && authorities.stream().anyMatch(a -> a.getAuthority().equals("SD"))) {
                                response.sendRedirect(contextPath + "/executivehome");
                            
                            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("Technician"))) {
                                response.sendRedirect(contextPath + "/successhome");

                            } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("SuperAdmin"))) {

                                response.sendRedirect(contextPath + "/superhome");
                            } else {
                                response.sendRedirect(contextPath + "/executivehome");
                            }
                        })
                        .permitAll())
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll())
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/403"))
                .csrf(csrf -> csrf.disable());

		return http.build();
	}

	@Bean
	HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
		return new HttpSessionSecurityContextRepository();
	}
	
}
