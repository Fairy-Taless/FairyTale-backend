package fairytale.tbd.domain.user.service;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.web.dto.UserRequestDTO;

public interface UserCommandService {
	public User addUser(UserRequestDTO.AddUserDTO request);
}
