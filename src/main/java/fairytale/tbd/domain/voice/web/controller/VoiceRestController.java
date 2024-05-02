package fairytale.tbd.domain.voice.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.voice.converter.VoiceConverter;
import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.service.VoiceCommandService;
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

	@PostMapping("")
	public ApiResponse<VoiceResponseDTO.AddVoiceResultDTO> addVoice(
		@Valid @ModelAttribute VoiceRequestDTO.AddVoiceDTO request, @LoginUser User user) {
		LOGGER.info("request = {}", request);
		Voice voice = voiceCommandService.uploadVoice(request, user);
		return ApiResponse.onSuccess(VoiceConverter.toAddVoiceResult(voice));
	}

	@PostMapping("/segment")
	public ApiResponse<VoiceResponseDTO.AddTTSSegmentResultDTO> addSegment(
		@Valid @RequestBody VoiceRequestDTO.AddSegmentDTO request) {
		VoiceResponseDTO.AddTTSSegmentResultDTO result = voiceCommandService.addTTSSegment(request);
		return ApiResponse.onSuccess(result);
	}

}
