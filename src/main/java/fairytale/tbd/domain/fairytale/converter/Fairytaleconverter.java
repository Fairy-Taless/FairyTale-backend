package fairytale.tbd.domain.fairytale.converter;

import java.util.ArrayList;

import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.fairytale.web.dto.FairytaleRequestDTO;
import fairytale.tbd.domain.fairytale.web.dto.FairytaleResponseDTO;

public class Fairytaleconverter {
	public static Fairytale toFairytale(FairytaleRequestDTO.AddFairytaleRequestDTO request) {
		return Fairytale.builder()
			.name(request.getName())
			.segmentList(new ArrayList<>())
			.build();
	}

	public static FairytaleResponseDTO.AddFairytaleResultDTO toAddFairytaleResultDTO(Fairytale fairytale) {
		return FairytaleResponseDTO.AddFairytaleResultDTO.builder()
			.id(fairytale.getId())
			.createdAt(fairytale.getCreatedAt())
			.build();
	}
}
