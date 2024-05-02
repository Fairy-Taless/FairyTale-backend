package fairytale.tbd.global.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import fairytale.tbd.domain.user.repository.UserRepository;
import fairytale.tbd.domain.user.service.UserQueryService;
import fairytale.tbd.global.security.LoginService;
import fairytale.tbd.global.security.jwt.JwtService;
import fairytale.tbd.global.security.jwt.filter.CustomUsernamePwdAuthenticationFilter;
import fairytale.tbd.global.security.jwt.filter.JwtAuthenticationFilter;
import fairytale.tbd.global.security.jwt.handler.JwtLoginFailureHandler;
import fairytale.tbd.global.security.jwt.handler.JwtLoginSuccessHandler;
import lombok.RequiredArgsConstructor;

/**
 * 인증은 CustomJsonUsernamePasswordAuthenticationFilter에서 authenticate()로 인증된 사용자로 처리
 * JwtAuthenticationProcessingFilter는 AccessToken, RefreshToken 재발급
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final LoginService loginService;
	private final JwtService jwtService;
	private final UserRepository userRepository;
	private final ObjectMapper objectMapper;
	private final JwtLoginSuccessHandler jwtLoginSuccessHandler;
	private final JwtLoginFailureHandler jwtLoginFailureHandler;
	private final UserQueryService userQueryService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable) // csrf 보안 사용 X => Rest API
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(
				SessionCreationPolicy.STATELESS)) // Token 기반 인증 => session 사용 X
			.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/login", "/api/user/signup").permitAll() // 허용된 주소
				.anyRequest().authenticated()
			)
			// CORS
			.cors(corsCustomizer -> corsCustomizer.configurationSource(request -> {
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(List.of("*");
				config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
				config.setAllowCredentials(true);
				config.setAllowedHeaders(List.of("*"));
				config.setExposedHeaders(Arrays.asList("Authorization", "Authorization-refresh"));
				config.setMaxAge(3600L);

				UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
				source.registerCorsConfiguration("/**", config);
				return source;
			}));
		http.addFilterAfter(customUsernamePwdAuthenticationFilter(), LogoutFilter.class);
		http.addFilterBefore(jwtAuthenticationFilter(), CustomUsernamePwdAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(loginService);
		return new ProviderManager(provider);
	}

	@Bean
	public CustomUsernamePwdAuthenticationFilter customUsernamePwdAuthenticationFilter() {
		CustomUsernamePwdAuthenticationFilter customJsonUsernamePasswordLoginFilter
			= new CustomUsernamePwdAuthenticationFilter(objectMapper);
		customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
		customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(jwtLoginSuccessHandler);
		customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(jwtLoginFailureHandler);
		return customJsonUsernamePasswordLoginFilter;
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService,
			userRepository, userQueryService);
		return jwtAuthenticationFilter;
	}
}
