package fairytale.tbd.domain.voice.web.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class VoiceRequestDTO {
	@Getter
	@Setter
	public static class AddVoiceDTO{
		//파일 형식 검증
		@NotNull
		MultipartFile sample;

	}
}
