package fairytale.tbd.global.security;

import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

	private final UserRepository userRepository;
	private static final Logger LOGGER = LogManager.getLogger(UserDetailsService.class);

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

		User user = userRepository.findByLoginId(loginId)
			.orElseThrow(() -> new UsernameNotFoundException("해당 아이디가 존재하지 않습니다."));

		LOGGER.info("loadUserByUsername = {}", user);

		return org.springframework.security.core.userdetails.User.builder()
			.username(user.getLoginId())
			.authorities(user.getAuthorityList()
				.stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getRole().toString()))
				.collect(
					Collectors.toSet()))
			.password(user.getPassword())
			.build();
	}
}
