package fairytale.tbd.domain.user.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDTO {
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AddUserResultDTO{
		private Long userId;
		private LocalDateTime createdAt;
	}
}
