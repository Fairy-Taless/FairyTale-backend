package fairytale.tbd.domain.voice.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fairytale.tbd.domain.voice.converter.VoiceConverter;
import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.service.VoiceCommandService;
import fairytale.tbd.domain.voice.web.dto.VoiceRequestDTO;
import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;
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
	public ApiResponse<VoiceResponseDTO.AddVoiceResultDTO> addVoice(@Valid @ModelAttribute VoiceRequestDTO.AddVoiceDTO request){
		// TODO 이미 존재하는 Voice 인지 검증
		LOGGER.info("request = {}", request);
		Voice voice = voiceCommandService.uploadVoice(request);
		return ApiResponse.onSuccess(VoiceConverter.toAddVoiceResult(voice));
	}


}
