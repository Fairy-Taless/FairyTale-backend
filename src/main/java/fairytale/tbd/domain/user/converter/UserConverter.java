package fairytale.tbd.domain.user.converter;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.web.dto.UserRequestDTO;
import fairytale.tbd.domain.user.web.dto.UserResponseDTO;

public class UserConverter {
	public static User toUser(UserRequestDTO.AddUserDTO request){
		return User.builder()
			.loginId(request.getLoginId())
			.password(request.getPassword())
			.username(request.getUsername())
			.gender(request.getGender())
			.build();
	}

	public static UserResponseDTO.AddUserResultDTO toAddUserResultDTO(User user){
		return UserResponseDTO.AddUserResultDTO.builder()
			.userId(user.getId())
			.createdAt(user.getCreatedAt())
			.build();
	}
}
