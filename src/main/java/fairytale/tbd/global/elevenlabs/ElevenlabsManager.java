package fairytale.tbd.global.elevenlabs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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

import fairytale.tbd.global.elevenlabs.exception.FileConvertException;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/**
 * https://github.com/Andrewcpu/elevenlabs-api
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenlabsManager {

	@Value("${voice.elevenlabs.apikey}")
	private String apiKey;

	@Value("${voice.elevenlabs.voice_setting.similarity_boost}")
	private double similarity;
	@Value("${voice.elevenlabs.voice_setting.stability}")
	private double stability;
	@Value("${voice.elevenlabs.voice_setting.style}")
	private double style;
	@Value("${voice.elevenlabs.voice_setting.use_speaker_boost}")
	private boolean useSpeakerBoost;

	/**
	 * EleventLabs TTS 변환
	 * @param text 음성 TTS 변환 할 내용
	 * @param voiceId voice 고유 값
	 * @return 생성된 .mpga 파일
	 */

	public File elevenLabsTTS(String text, String voiceId){
		ElevenLabs.setApiKey(apiKey);
		ElevenLabs.setDefaultModel("eleven_multilingual_v2");
		return SpeechGenerationBuilder.textToSpeech()
			.file()
			.setText(text)
			.setGeneratedAudioOutputFormat(GeneratedAudioOutputFormat.MP3_44100_128)
			.setVoiceId(voiceId)
			.setVoiceSettings(new VoiceSettings(stability, similarity, style, useSpeakerBoost))
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
		VoiceBuilder builder = new VoiceBuilder();
		builder.withName(userName);
		builder.withFile(convertMultipartFileToFile(sample));
		builder.withLabel("language", "ko");
		Voice voice = builder.create();
		return voice.getVoiceId();
	}


	// TODO IOException 예외 처리 => 500

	/**
	 * MultiPartFile -> File 변환
	 * @param multipartFile
	 * @return
	 */
	public static File convertMultipartFileToFile(MultipartFile multipartFile) {
		// 임시 파일 생성
		File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());

		// MultipartFile의 내용을 파일에 쓰기
		try (InputStream inputStream = multipartFile.getInputStream();
			 FileOutputStream outputStream = new FileOutputStream(file)) {
			int read;
			byte[] bytes = new byte[1024];
			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
		}
		catch (Exception e){
			e.printStackTrace();
			throw new FileConvertException(ErrorStatus._FILE_CONVERT_ERROR);
		}

		return file;
	}
}
