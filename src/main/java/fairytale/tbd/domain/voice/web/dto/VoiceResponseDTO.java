package fairytale.tbd.domain.voice.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VoiceResponseDTO {
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AddVoiceResultDTO{
		private Long voiceId;
		private LocalDateTime createdAt;
	}
}
