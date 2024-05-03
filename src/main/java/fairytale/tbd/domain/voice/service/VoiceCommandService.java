package fairytale.tbd.domain.voice.service;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.voice.entity.Segment;
import fairytale.tbd.domain.voice.entity.TTSSegment;
import fairytale.tbd.domain.voice.entity.UserTTSSegment;
import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.web.dto.VoiceRequestDTO;
import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;

public interface VoiceCommandService {
	Voice uploadVoice(VoiceRequestDTO.AddVoiceDTO request, User user);

	VoiceResponseDTO.AddTTSSegmentResultDTO addTTSSegment(VoiceRequestDTO.AddSegmentDTO request);

	TTSSegment saveTTSSegment(Segment segment);

	UserTTSSegment saveUserTTSSegment(User user, Segment segment);
}
