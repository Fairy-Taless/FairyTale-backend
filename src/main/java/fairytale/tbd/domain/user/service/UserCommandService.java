package fairytale.tbd.domain.user.service;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.web.dto.UserRequestDTO;

public interface UserCommandService {
	User addUser(UserRequestDTO.AddUserDTO request);

	void setUserImageUrl(String imageUrl, User user);

	void setUserImageOpts(String imageOpts, User user);
}
