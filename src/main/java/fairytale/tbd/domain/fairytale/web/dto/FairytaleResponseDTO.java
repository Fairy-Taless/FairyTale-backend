package fairytale.tbd.domain.fairytale.web.dto;

import java.time.LocalDateTime;
import java.util.List;

import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class FairytaleResponseDTO {
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AddFairytaleResultDTO {
		private Long id;
		private LocalDateTime createdAt;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GetFairytaleDetailDTO {
		private List<VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO> voice_list;
		private String image_url;
	}
}
