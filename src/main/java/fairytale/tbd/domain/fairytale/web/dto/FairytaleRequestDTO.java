package fairytale.tbd.domain.fairytale.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class FairytaleRequestDTO {
	@Getter
	@Setter
	public static class AddFairytaleRequestDTO {
		@NotBlank
		private String name;
	}
}
