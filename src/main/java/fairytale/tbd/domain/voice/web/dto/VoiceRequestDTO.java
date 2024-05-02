package fairytale.tbd.domain.voice.web.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import fairytale.tbd.domain.voice.enums.VoiceType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class VoiceRequestDTO {
	@Getter
	@Setter
	public static class AddVoiceDTO {
		//파일 형식 검증
		@NotNull
		MultipartFile sample;
	}

	@Getter
	@Setter
	@ToString
	public static class AddSegmentDTO {
		private String context;
		@JsonProperty("isMainCharacter")
		private boolean isMainCharacter;
		private VoiceType voiceType;
		private Long fairytaleId;
		private Double segmentNum;
		private Long pageNum;
	}

}
