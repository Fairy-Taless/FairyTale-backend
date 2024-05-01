package fairytale.tbd.domain.voice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.voice.converter.VoiceConverter;
import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.exception.ExistVoiceException;
import fairytale.tbd.domain.voice.exception.VoiceSaveErrorException;
import fairytale.tbd.domain.voice.repository.VoiceRepository;
import fairytale.tbd.domain.voice.web.dto.VoiceRequestDTO;
import fairytale.tbd.global.elevenlabs.ElevenlabsManager;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoiceCommandServiceImpl implements VoiceCommandService {

	private static final Logger LOGGER = LogManager.getLogger(VoiceCommandServiceImpl.class);

	private final ElevenlabsManager elevenlabsManager;
	private final VoiceRepository voiceRepository;

	/**
	 * ElevenLabs Voice 추가
	 * @param request MultiPartFile sample 사용자 녹음 파일
	 */

	@Transactional
	@Override
	public Voice uploadVoice(VoiceRequestDTO.AddVoiceDTO request, User user) {
		// 사용자의 목소리가 이미 저장되어 있으면 오류
		if (voiceRepository.existsVoiceByUserId(user.getId())) {
			LOGGER.error("이미 존재하는 목소리 === userId = {}", user.getId());
			throw new ExistVoiceException(ErrorStatus._EXIST_VOICE);
		}

		String keyId = elevenlabsManager.addVoice(user.getUsername(), request.getSample());
		if (keyId == null) {
			LOGGER.error("Eleven Labs 음성 저장에 실패했습니다.");
			throw new VoiceSaveErrorException(ErrorStatus._VOICE_SAVE_ERROR);
		}

		Voice voice = VoiceConverter.toVoice(keyId);
		user.setVoice(voice);
		voiceRepository.save(voice);

		return voice;
	}

}
