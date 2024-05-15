package fairytale.tbd.domain.voice.web.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class VoiceResponseDTO {
	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AddVoiceResultDTO {
		private Long voiceId;
		private LocalDateTime createdAt;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AddTTSSegmentResultDTO {
		private Long segmentId;
		private Long ttsSegmentId;
		private String url;
		private LocalDateTime createdAt;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GetUserTTSSegmentResultWithDateDTO {
		Map<Long, List<GetUserTTSSegmentResultDetailDTO>> ttsSegmentResultList;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GetUserTTSSegmentResultDTO {
		List<GetUserTTSSegmentResultDetailDTO> ttsSegmentList;
	}

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class GetUserTTSSegmentResultDetailDTO {
		private String audioUrl;
		private Long segmentId;
	}
}
