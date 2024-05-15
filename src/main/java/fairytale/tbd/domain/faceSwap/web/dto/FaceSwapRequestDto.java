package fairytale.tbd.domain.faceSwap.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
public class FaceSwapRequestDto {

	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FaceSwapRequest {

		private SourceImage sourceImage;

		private TargetImage targetImage;

		private String modifyImage;

		private String webhookUrl;
	}

	// Original Image
	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SourceImage {

		private String sourcePath;

		private String sourceOpts;

	}

	// User Image
	@Getter
	@Setter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class TargetImage {

		private String targetPath;

		private String targetOpts;

	}
}
