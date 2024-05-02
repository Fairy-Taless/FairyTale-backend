package fairytale.tbd.domain.fairytale.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fairytale.tbd.domain.fairytale.converter.Fairytaleconverter;
import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.fairytale.service.FairytaleCommandService;
import fairytale.tbd.domain.fairytale.web.dto.FairytaleRequestDTO;
import fairytale.tbd.domain.fairytale.web.dto.FairytaleResponseDTO;
import fairytale.tbd.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fairytale")
public class FairytaleRestController {
	private final FairytaleCommandService fairytaleCommandService;

	/**
	 * 동화 추가 메서드
	 */
	@PostMapping("")
	public ApiResponse<FairytaleResponseDTO.AddFairytaleResultDTO> addFairytale(@Valid @RequestBody
	FairytaleRequestDTO.AddFairytaleRequestDTO request) {
		Fairytale fairytale = fairytaleCommandService.saveFairytale(request);
		return ApiResponse.onSuccess(Fairytaleconverter.toAddFairytaleResultDTO(fairytale));
	}

}
