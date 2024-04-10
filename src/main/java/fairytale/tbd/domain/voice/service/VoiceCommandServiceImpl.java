package fairytale.tbd.domain.voice.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.andrewcpu.elevenlabs.ElevenLabs;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.repository.UserRepository;
import fairytale.tbd.domain.voice.converter.VoiceConverter;
import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.repository.VoiceRepository;
import fairytale.tbd.domain.voice.web.dto.VoiceRequestDTO;
import fairytale.tbd.global.elevenlabs.ElevenlabsManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoiceCommandServiceImpl implements VoiceCommandService{

	private static final Logger LOGGER = LogManager.getLogger(VoiceCommandServiceImpl.class);

	private final ElevenlabsManager elevenlabsManager;
	private final UserRepository userRepository;
	private final VoiceRepository voiceRepository;

	/**
	 * ElevenLabs Voice 추가
	 * @param request MultiPartFile sample 사용자 녹음 파일
	 */

	@Transactional
	@Override
	public Voice uploadVoice(VoiceRequestDTO.AddVoiceDTO request) {

		Optional<User> userOptional = userRepository.findById(6L);
		User user = userOptional.get();

		// TODO username session에서 가져오기
		String keyId = elevenlabsManager.addVoice("test", request.getSample());

		Voice voice = VoiceConverter.toVoice(keyId);
		user.setVoice(voice);
		voiceRepository.save(voice);

		return voice;
	}


}
