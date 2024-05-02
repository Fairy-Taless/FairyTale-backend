package fairytale.tbd.domain.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fairytale.tbd.domain.user.converter.UserConverter;
import fairytale.tbd.domain.user.entity.Authority;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.enums.Role;
import fairytale.tbd.domain.user.repository.UserRepository;
import fairytale.tbd.domain.user.web.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCommandServiceImpl implements UserCommandService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	@Override
	public User addUser(UserRequestDTO.AddUserDTO request) {
		// 비밀번호 암호화
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		User user = UserConverter.toUser(request, encodedPassword);
		// 유저 권한 추가
		Authority authority = Authority.builder()
			.role(Role.ROLE_USER)
			.build();
		user.addAuthority(authority);
		return userRepository.save(user);
	}
}
