package fairytale.tbd.global.elevenlabs;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import net.andrewcpu.elevenlabs.ElevenLabs;
import net.andrewcpu.elevenlabs.builders.SpeechGenerationBuilder;
import net.andrewcpu.elevenlabs.enums.GeneratedAudioOutputFormat;
import net.andrewcpu.elevenlabs.enums.StreamLatencyOptimization;
import net.andrewcpu.elevenlabs.model.voice.Voice;
import net.andrewcpu.elevenlabs.model.voice.VoiceBuilder;
import net.andrewcpu.elevenlabs.model.voice.VoiceSettings;

import fairytale.tbd.domain.voice.enums.VoiceType;
import fairytale.tbd.domain.voice.exception.InvalidVoiceTypeException;
import fairytale.tbd.global.elevenlabs.exception.FileConvertException;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * https://github.com/Andrewcpu/elevenlabs-api
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenlabsManager {

	private static final Logger LOGGER = LogManager.getLogger(ElevenlabsManager.class);

	@Value("${voice.elevenlabs.apikey}")
	private String apiKey;

	@Value("${voice.elevenlabs.voice_setting.similarity_boost}")
	private double similarity;
	@Value("${voice.elevenlabs.voice_setting.stability}")
	private double stability;
	@Value("${voice.elevenlabs.voice_setting.style}")
	private double style;

	@Value("${voice.elevenlabs.narration-voice-setting.similarity_boost}")
	private double Nsimilarity;
	@Value("${voice.elevenlabs.narration-voice-setting.stability}")
	private double Nstability;
	@Value("${voice.elevenlabs.narration-voice-setting.style}")
	private double Nstyle;
	@Value("${voice.elevenlabs.narration-voice-setting.use-speaker-boost}")
	private boolean NuseSpeakerBoost;

	@Value("${voice.elevenlabs.voice_setting.use_speaker_boost}")
	private boolean useSpeakerBoost;

	@Value("${voice.elevenlabs.common-voice-id.old-man}")
	private String oldManKey;

	@Value("${voice.elevenlabs.common-voice-id.old-woman}")
	private String oldWomanKey;

	@Value("${voice.elevenlabs.common-voice-id.middle-age-man}")

	private String middleAgeManKey;

	@Value("${voice.elevenlabs.common-voice-id.middle-age-woman}")
	private String middleAgeWomanKey;

	@Value("${voice.elevenlabs.common-voice-id.young-man}")
	private String youngManKey;

	@Value("${voice.elevenlabs.common-voice-id.young_woman}")
	private String youngWomanKey;

	@Value("${voice.elevenlabs.common-voice-id.narration-woman}")
	private String narrationWomanKey;

	@PostConstruct
	public void init() {
		ElevenLabs.setApiKey(apiKey);
		ElevenLabs.setDefaultModel("eleven_multilingual_v2");
	}

	/**
	 * EleventLabs TTS 변환
	 * @param text 음성 TTS 변환 할 내용
	 * @param voiceId voice 고유 값
	 * @return 생성된 .mpga 파일
	 */
	public File elevenLabsTTS(String text, String voiceId) {
		return SpeechGenerationBuilder.textToSpeech()
			.file()
			.setText(text)
			.setGeneratedAudioOutputFormat(GeneratedAudioOutputFormat.MP3_44100_128)
			.setVoiceId(voiceId)
			.setVoiceSettings(new VoiceSettings(stability, similarity, style, useSpeakerBoost))
			.setLatencyOptimization(StreamLatencyOptimization.NONE)
			.build();
	}

	public File elevenLabsTTS(String text, VoiceType voiceType) {
		if (voiceType == VoiceType.NARRATION) {
			return elevenLabsTTSNarration(text);
		}
		return elevenLabsTTS(text, getCommonVoiceId(voiceType));
	}

	private String getCommonVoiceId(VoiceType voiceType) {
		switch (voiceType) {
			case OLD_MAN:
				return oldManKey;
			case OLD_WOMAN:
				return oldWomanKey;
			case YOUNG_MAN:
				return youngManKey;
			case YOUNG_WOMAN:
				return youngWomanKey;
			case MIDDLEAGE_MAN:
				return middleAgeManKey;
			case MIDDLEAGE_WOMAN:
				return middleAgeWomanKey;
			default:
				throw new InvalidVoiceTypeException(ErrorStatus._INVALID_VOICE_TYPE);
		}

	}

	/**
	 * EleventLabs TTS 나레이션 변환
	 * @param text 음성 TTS 변환 할 내용
	 * @return 생성된 .mpga 파일
	 */
	public File elevenLabsTTSNarration(String text) {
		return SpeechGenerationBuilder.textToSpeech()
			.file()
			.setText(text)
			.setGeneratedAudioOutputFormat(GeneratedAudioOutputFormat.MP3_44100_128)
			.setVoiceId(narrationWomanKey)
			.setVoiceSettings(new VoiceSettings(Nstability, Nsimilarity, Nstyle, NuseSpeakerBoost))
			.setLatencyOptimization(StreamLatencyOptimization.NONE)
			.build();
	}

	/**
	 * Voice 생성
	 * @param userName 유저 이름 (voice에 저장될 이름)
	 * @param sample 실제 음성 녹음 파일
	 * @return voice 고유번호
	 */
	public String addVoice(String userName, MultipartFile sample) {
		File file = convertMultipartFileToFile(sample);
		VoiceBuilder builder = new VoiceBuilder();
		builder.withName(userName);
		builder.withFile(file);
		builder.withDescription("the emotional voice of the main character of a children's book");
		builder.withLabel("language", "ko");
		Voice voice = builder.create();
		if (file != null && file.exists()) {
			file.delete();
		}
		return voice.getVoiceId();
	}

	/**
	 * MultiPartFile -> File 변환
	 * @param multipartFile
	 * @return
	 */
	public static File convertMultipartFileToFile(MultipartFile multipartFile) {
		// 임시 파일 생성
		File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());

		// MultipartFile의 내용을 파일에 쓰기
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileConvertException(ErrorStatus._FILE_CONVERT_ERROR);
		}

		return file;
	}
}
