package fairytale.tbd.domain.user.service;

import java.util.Optional;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.web.dto.UserResponseDTO;

public interface UserQueryService {

	Optional<User> getUserWithAuthorities(String loginId);

	void updateRefreshToken(User user, String reIssuedRefreshToken);

	Optional<User> getUserWithUserId(Long userId);

	UserResponseDTO.MyPageGetResultDto getUserMyPage(User user);
}
