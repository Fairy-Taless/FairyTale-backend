package fairytale.tbd.domain.faceSwap.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FaceResponseDTO {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class OriginalCharacterSaveResponseDTO {
		private Long original_character_id;
		private String image_url;
		private LocalDateTime created_at;
	}
}
