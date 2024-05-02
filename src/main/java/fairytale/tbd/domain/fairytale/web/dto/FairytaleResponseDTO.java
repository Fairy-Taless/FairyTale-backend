package fairytale.tbd.domain.fairytale.web.dto;

import java.time.LocalDateTime;

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
}
