package fairytale.tbd.domain.voice.converter;

import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;

public class VoiceConverter {

	public static VoiceResponseDTO.AddVoiceResultDTO toAddVoiceResult(Voice voice){
		return VoiceResponseDTO.AddVoiceResultDTO.builder()
			.voiceId(voice.getId())
			.createdAt(voice.getCreatedAt())
			.build();
	}

	public static Voice toVoice(String keyId){
		return Voice.builder()
			.keyId(keyId)
			.build();
	}
}
