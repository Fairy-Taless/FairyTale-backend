package fairytale.tbd.domain.faceSwap.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class WebhookRequestDTO {
	@Getter
	@Setter
	@ToString
	public static class RequestDTO{
		private String signature;
		private String dataEncrypt;
		private String timestamp;
		private String nonce;

	}
}
