package fairytale.tbd.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService {

	private final UserRepository userRepository;

	@Override
	public Optional<User> getUserWithAuthorities(String loginId) {
		User user = userRepository.findByLoginId(loginId).orElse(null);
		user.getAuthorityList().size();
		return Optional.ofNullable(user);
	}

	@Transactional
	@Override
	public void updateRefreshToken(User user, String reIssuedRefreshToken) {
		user.updateRefreshToken(reIssuedRefreshToken);
		userRepository.saveAndFlush(user);
	}

}
