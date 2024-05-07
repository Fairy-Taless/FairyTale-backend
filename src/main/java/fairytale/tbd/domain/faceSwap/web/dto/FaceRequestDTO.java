package fairytale.tbd.domain.faceSwap.web.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

public class FaceRequestDTO {

	@Getter
	@Setter
	public static class OriginalCharacterSaveRequestDTO {
		private MultipartFile file;
		private Long page_num;
		private Long fairytale_id;
	}
}
