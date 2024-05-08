package fairytale.tbd.domain.voice.service;

import java.util.List;
import java.util.Map;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;

public interface VoiceQueryService {
	Map<Long, List<VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO>> getUserTTSSegmentList(User user,
		Long fairytaleId, boolean changeVoice);
}
