package fairytale.tbd.domain.voice.service;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.web.dto.VoiceRequestDTO;
import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;

public interface VoiceCommandService {
	Voice uploadVoice(VoiceRequestDTO.AddVoiceDTO request, User user);

	VoiceResponseDTO.AddTTSSegmentResultDTO addTTSSegment(VoiceRequestDTO.AddSegmentDTO request);
}
