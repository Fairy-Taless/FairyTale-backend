package fairytale.tbd.domain.voice.service;

import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.web.dto.VoiceRequestDTO;

public interface VoiceCommandService {
	Voice uploadVoice(VoiceRequestDTO.AddVoiceDTO request);
}
