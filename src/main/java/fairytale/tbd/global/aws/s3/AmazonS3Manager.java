package fairytale.tbd.global.aws.s3;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import fairytale.tbd.global.config.AmazonConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager {

	private final AmazonS3 amazonS3;
	private final AmazonConfig amazonConfig;

	@Value("${cloud.aws.s3.path.voice-sample}")
	public String VOICE_SAMPLE_PATH;

	@Value("${cloud.aws.s3.path.common-voice}")
	public String TTS_COMMON_VOICE_PATH;

	@Value("${cloud.aws.s3.path.user-voice}")
	public String TTS_USER_VOICE_PATH;

	public String uploadFile(String keyName, MultipartFile file) {
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(file.getContentType());
		metadata.setContentLength(file.getSize());
		try {
			PutObjectResult putObjectResult = amazonS3.putObject(
				new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
			log.info("result={}", putObjectResult.getContentMd5());
		} catch (IOException e) {
			log.error("error at AmazonS3Manager uploadFile : {}", (Object)e.getStackTrace());
		}

		return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
	}

	public String generateS3SavePath(String path) {
		return path + '/' + UUID.randomUUID().toString();
	}

	public String generateS3SavePath(String path, String name) {
		return path + '/' + name;
	}

}
