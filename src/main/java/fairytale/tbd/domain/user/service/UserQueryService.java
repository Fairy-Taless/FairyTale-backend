package fairytale.tbd.domain.user.service;

import java.util.Optional;

import fairytale.tbd.domain.user.entity.User;

public interface UserQueryService {

	Optional<User> getUserWithAuthorities(String loginId);

	void updateRefreshToken(User user, String reIssuedRefreshToken);

	Optional<User> getUserWithUserId(Long userId);
}
