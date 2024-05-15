package fairytale.tbd.domain.user.converter;

import java.util.ArrayList;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.web.dto.UserRequestDTO;
import fairytale.tbd.domain.user.web.dto.UserResponseDTO;

public class UserConverter {
	public static User toUser(UserRequestDTO.AddUserDTO request, String encodedPassword) {
		return User.builder()
			.loginId(request.getLoginId())
			.password(encodedPassword)
			.username(request.getUsername())
			.gender(request.getGender())
			.authorityList(new ArrayList<>())
			.build();
	}

	public static UserResponseDTO.AddUserResultDTO toAddUserResultDTO(User user) {
		return UserResponseDTO.AddUserResultDTO.builder()
			.userId(user.getId())
			.createdAt(user.getCreatedAt())
			.build();
	}
}
