package fairytale.tbd.domain.voice.web.controller;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.voice.converter.VoiceConverter;
import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.service.VoiceCommandService;
import fairytale.tbd.domain.voice.service.VoiceQueryService;
import fairytale.tbd.domain.voice.web.dto.VoiceRequestDTO;
import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;
import fairytale.tbd.global.annotation.LoginUser;
import fairytale.tbd.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voice")
public class VoiceRestController {

	private static final Logger LOGGER = LogManager.getLogger(VoiceRestController.class);
	private final VoiceCommandService voiceCommandService;
	private final VoiceQueryService voiceQueryService;

	/**
	 * 사용자의 음성 추가
	 */
	@PostMapping("")
	public ApiResponse<VoiceResponseDTO.AddVoiceResultDTO> addVoice(
		@Valid @ModelAttribute VoiceRequestDTO.AddVoiceDTO request, @LoginUser User user) {
		LOGGER.info("request = {}", request);
		Voice voice = voiceCommandService.uploadVoice(request, user);
		return ApiResponse.onSuccess(VoiceConverter.toAddVoiceResult(voice));
	}

	/**
	 * 동화의 문장 추가
	 */
	@PostMapping("/segment")
	public ApiResponse<VoiceResponseDTO.AddTTSSegmentResultDTO> addSegment(
		@Valid @RequestBody VoiceRequestDTO.AddSegmentDTO request) {
		LOGGER.info("::: Segment 추가 요청 :::");
		LOGGER.info("request = {}", request);
		VoiceResponseDTO.AddTTSSegmentResultDTO result = voiceCommandService.addTTSSegment(request);
		LOGGER.info("::: Segment 추가 성공 SegmentId = {}:::", result.getSegmentId());
		return ApiResponse.onSuccess(result);
	}

	@GetMapping("/segment/test")
	public ApiResponse<Map<Long, List<VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO>>> getUserSegment(
		@LoginUser User user,
		@RequestParam(name = "fairytaleId") Long fairytaleId) {
		LOGGER.info("getUserSegment START");
		Map<Long, List<VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO>> userTTSSegmentList = voiceQueryService.getUserTTSSegmentList(
			user, fairytaleId, true);
		return ApiResponse.onSuccess(userTTSSegmentList);
	}

}
