package fairytale.tbd.global.aws.s3;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import fairytale.tbd.global.config.AmazonConfig;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import fairytale.tbd.global.exception.GeneralException;
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

	@Value("${cloud.aws.s3.path.face-sample}")
	public String FACE_SWAP_SAMPLE_PATH;

	@Value("${cloud.aws.s3.path.user-face}")
	public String FACE_SWAP_USER_PATH;

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
			throw new GeneralException(ErrorStatus._S3_FILE_UPLOAD_ERROR);
		}

		return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
	}

	public String uploadJpegImageFromUrl(String keyName, String imageUrl) {

		try {
			URL url = new URL(imageUrl);
			InputStream inputStream = url.openStream();
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType("image/jpeg"); // Change content type based on your image type
			metadata.setContentLength(inputStream.available());

			PutObjectResult putObjectResult = amazonS3.putObject(
				new PutObjectRequest(amazonConfig.getBucket(), keyName, inputStream, metadata));
			log.info("result={}", putObjectResult.getContentMd5());
		} catch (IOException e) {
			log.error("error at AmazonS3Manager uploadFile : {}", (Object)e.getStackTrace());
			throw new GeneralException(ErrorStatus._S3_FILE_UPLOAD_ERROR);
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
