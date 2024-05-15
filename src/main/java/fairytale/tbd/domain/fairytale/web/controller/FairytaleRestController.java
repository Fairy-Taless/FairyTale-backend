package fairytale.tbd.domain.fairytale.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fairytale.tbd.domain.faceSwap.service.FaceSwapQueryService;
import fairytale.tbd.domain.fairytale.converter.Fairytaleconverter;
import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.fairytale.service.FairytaleCommandService;
import fairytale.tbd.domain.fairytale.web.dto.FairytaleRequestDTO;
import fairytale.tbd.domain.fairytale.web.dto.FairytaleResponseDTO;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.voice.service.VoiceQueryService;
import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;
import fairytale.tbd.global.annotation.LoginUser;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import fairytale.tbd.global.exception.GeneralException;
import fairytale.tbd.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fairytale")
public class FairytaleRestController {
	private static final Logger LOGGER = LogManager.getLogger(FairytaleRestController.class);

	private final FairytaleCommandService fairytaleCommandService;
	private final VoiceQueryService voiceQueryService;
	private final FaceSwapQueryService faceSwapQueryService;

	/**
	 * 동화 추가 메서드
	 */
	@PostMapping("")
	public ApiResponse<FairytaleResponseDTO.AddFairytaleResultDTO> addFairytale(@Valid @RequestBody
	FairytaleRequestDTO.AddFairytaleRequestDTO request) {
		Fairytale fairytale = fairytaleCommandService.saveFairytale(request);
		return ApiResponse.onSuccess(Fairytaleconverter.toAddFairytaleResultDTO(fairytale));
	}

	/**
	 * 동화 데이터 조회 메서드
	 */
	@GetMapping("/{fairytaleId}")
	public ApiResponse<Map<Long, FairytaleResponseDTO.GetFairytaleDetailDTO>> getFairytaleWithId(
		@LoginUser User user,
		@PathVariable(name = "fairytaleId") Long fairytaleId,
		@RequestParam(name = "change_voice") boolean changeVoice,
		@RequestParam(name = "change_face") boolean changeFace) {

		Map<Long, List<VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO>> userTTSSegmentList = voiceQueryService.getUserTTSSegmentList(
			user, fairytaleId, changeVoice);

		Map<Long, String> faceImages = faceSwapQueryService.getFaceImages(user, fairytaleId, changeFace);

		if (!userTTSSegmentList.keySet().equals(faceImages.keySet())) {
			LOGGER.error("동화 데이터 호출 중 에러가 발생했습니다.");
			throw new GeneralException(ErrorStatus._FAIRYTALE_DATA_NOT_EXIST);
		}

		Map<Long, FairytaleResponseDTO.GetFairytaleDetailDTO> result = new HashMap<>();
		for (Long pageNum : userTTSSegmentList.keySet()) {

			FairytaleResponseDTO.GetFairytaleDetailDTO detailDTO = FairytaleResponseDTO.GetFairytaleDetailDTO.builder()
				.voice_list(userTTSSegmentList.get(pageNum))
				.image_url(faceImages.get(pageNum))
				.build();

			result.put(pageNum, detailDTO);
		}

		return ApiResponse.onSuccess(result);
	}
}
