package fairytale.tbd.domain.voice.service;

import java.io.File;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.fairytale.exception.FairytaleNotFoundException;
import fairytale.tbd.domain.fairytale.repository.FairytaleRepository;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.voice.converter.VoiceConverter;
import fairytale.tbd.domain.voice.entity.Segment;
import fairytale.tbd.domain.voice.entity.TTSSegment;
import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.domain.voice.entity.VoiceSample;
import fairytale.tbd.domain.voice.exception.ExistVoiceException;
import fairytale.tbd.domain.voice.exception.VoiceSaveErrorException;
import fairytale.tbd.domain.voice.repository.SegmentRepository;
import fairytale.tbd.domain.voice.repository.TTSSegmentRepository;
import fairytale.tbd.domain.voice.repository.VoiceRepository;
import fairytale.tbd.domain.voice.web.dto.VoiceRequestDTO;
import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;
import fairytale.tbd.global.aws.s3.AmazonS3Manager;
import fairytale.tbd.global.elevenlabs.ElevenlabsManager;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import fairytale.tbd.global.exception.GeneralException;
import fairytale.tbd.global.util.FileConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoiceCommandServiceImpl implements VoiceCommandService {

	private static final Logger LOGGER = LogManager.getLogger(VoiceCommandServiceImpl.class);

	private final ElevenlabsManager elevenlabsManager;
	private final VoiceRepository voiceRepository;
	private final AmazonS3Manager amazonS3Manager;
	private final FairytaleRepository fairytaleRepository;
	private final SegmentRepository segmentRepository;
	private final TTSSegmentRepository ttsSegmentRepository;

	/**
	 * ElevenLabs Voice 추가
	 * @param request MultiPartFile sample 사용자 녹음 파일
	 */

	@Transactional
	@Override
	public Voice uploadVoice(VoiceRequestDTO.AddVoiceDTO request, User user) {
		// 사용자의 목소리가 이미 저장되어 있으면 오류
		if (voiceRepository.existsVoiceByUserId(user.getId())) {
			LOGGER.error("=== 이미 존재하는 목소리 === userId = {}", user.getId());
			throw new ExistVoiceException(ErrorStatus._EXIST_VOICE);
		}

		String keyId = elevenlabsManager.addVoice(user.getUsername(), request.getSample());
		if (keyId == null) {
			LOGGER.error("Eleven Labs 음성 저장에 실패했습니다.");
			throw new VoiceSaveErrorException(ErrorStatus._VOICE_SAVE_ERROR);
		}

		String savePath = amazonS3Manager.uploadFile(
			amazonS3Manager.generateS3SavePath(amazonS3Manager.VOICE_SAMPLE_PATH),
			request.getSample());

		VoiceSample voiceSample = VoiceSample.builder()
			.url(savePath)
			.build();

		Voice voice = VoiceConverter.toVoice(keyId);
		voice.addVoiceSample(voiceSample);

		user.setVoice(voice);
		voiceRepository.save(voice);

		return voice;
	}

	@Transactional
	@Override
	public VoiceResponseDTO.AddTTSSegmentResultDTO addTTSSegment(VoiceRequestDTO.AddSegmentDTO request) {
		Fairytale fairytale = fairytaleRepository.findById(request.getFairytaleId())
			.orElseThrow(() -> new FairytaleNotFoundException(ErrorStatus._FAIRYTALE_NOT_FOUND));

		Segment segment = VoiceConverter.toSegment(request);
		fairytale.addSegment(segment);

		File file = elevenlabsManager.elevenLabsTTS(segment.getContext(), segment.getVoiceType());

		String uuid = UUID.randomUUID().toString();

		MultipartFile multipartFile;
		try {
			multipartFile = FileConverter.toMultipartFile(file, uuid);
		} catch (Exception e) {
			LOGGER.error("MultipartFile 변환 도중 에러 발생");
			throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
		}

		String savePath = amazonS3Manager.uploadFile(
			amazonS3Manager.generateS3SavePath(amazonS3Manager.TTS_COMMON_VOICE_PATH, uuid), multipartFile);

		TTSSegment ttsSegment = TTSSegment.builder()
			.url(savePath)
			.segment(segment)
			.build();

		TTSSegment save = ttsSegmentRepository.save(ttsSegment);
		return VoiceConverter.toAddSegmentResultDTO(save, segment.getId());
	}

}
