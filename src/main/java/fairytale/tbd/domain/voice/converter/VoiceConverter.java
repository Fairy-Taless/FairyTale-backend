package fairytale.tbd.domain.voice.converter;

import java.util.ArrayList;

import fairytale.tbd.domain.voice.entity.Segment;
import fairytale.tbd.domain.voice.entity.TTSSegment;
import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.web.dto.VoiceRequestDTO;
import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;

public class VoiceConverter {

	public static VoiceResponseDTO.AddVoiceResultDTO toAddVoiceResult(Voice voice) {
		return VoiceResponseDTO.AddVoiceResultDTO.builder()
			.voiceId(voice.getId())
			.createdAt(voice.getCreatedAt())
			.build();
	}

	public static Voice toVoice(String keyId) {
		return Voice.builder()
			.voiceSampleList(new ArrayList<>())
			.keyId(keyId)
			.build();
	}

	public static Segment toSegment(VoiceRequestDTO.AddSegmentDTO request) {
		return Segment.builder()
			.context(request.getContext())
			.isMainCharacter(request.isMainCharacter())
			.voiceType(request.getVoiceType())
			.num(request.getSegmentNum())
			.build();
	}

	public static VoiceResponseDTO.AddTTSSegmentResultDTO toAddSegmentResultDTO(TTSSegment ttsSegment, Long segmentId) {
		return VoiceResponseDTO.AddTTSSegmentResultDTO.builder()
			.segmentId(segmentId)
			.createdAt(ttsSegment.getCreatedAt())
			.ttsSegmentId(ttsSegment.getId())
			.url(ttsSegment.getUrl())
			.build();
	}
}
