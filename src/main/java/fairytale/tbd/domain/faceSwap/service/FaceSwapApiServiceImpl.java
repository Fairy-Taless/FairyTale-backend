package fairytale.tbd.domain.faceSwap.service;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fairytale.tbd.domain.faceSwap.entity.ImageSaveQueue;
import fairytale.tbd.domain.faceSwap.entity.OriginalCharacter;
import fairytale.tbd.domain.faceSwap.exception.FaceSwapFailureException;
import fairytale.tbd.domain.faceSwap.exception.UserFaceNotExistException;
import fairytale.tbd.domain.faceSwap.repository.ImageSaveQueueRepository;
import fairytale.tbd.domain.faceSwap.repository.OriginalCharacterRepository;
import fairytale.tbd.domain.faceSwap.util.SwapResult;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceRequestDTO;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapRequestDto;
import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.fairytale.exception.FairytaleNotFoundException;
import fairytale.tbd.domain.fairytale.repository.FairytaleRepository;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.global.aws.s3.AmazonS3Manager;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import fairytale.tbd.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FaceSwapApiServiceImpl implements FaceSwapApiService {

	@Value("${face.akool.apikey}")
	private String apikey;

	@Value("${face.akool.clientId}")
	private String clientId;

	private static final Logger LOGGER = LogManager.getLogger(FaceSwapApiServiceImpl.class);

	private final ObjectMapper objectMapper;

	private final ImageSaveQueueRepository imageSaveQueueRepository;

	private final FairytaleRepository fairytaleRepository;

	private final String WEBHOOK_URL = "http://13.125.16.41:8080/faceSwap/webhook";

	private final OriginalCharacterRepository originalCharacterRepository;

	private final AmazonS3Manager amazonS3Manager;
	private final FaceDetectApiService faceDetectApiService;

	@Transactional
	@Override
	public OriginalCharacter saveOriginalCharacter(FaceRequestDTO.OriginalCharacterSaveRequestDTO request) {

		Fairytale fairytale = fairytaleRepository.findById(request.getFairytale_id())
			.orElseThrow(() -> new FairytaleNotFoundException(ErrorStatus._FAIRYTALE_NOT_FOUND));

		String saveURL = amazonS3Manager.uploadFile(
			amazonS3Manager.generateS3SavePath(amazonS3Manager.FACE_SWAP_SAMPLE_PATH),
			request.getFile());

		FaceDetectRequestDto faceDetectRequestDto = new FaceDetectRequestDto(saveURL);
		String opts = faceDetectApiService.getOptFromFaceDetectApi(faceDetectRequestDto).getOpt();

		OriginalCharacter originalCharacter = OriginalCharacter.builder()
			.originalURL(saveURL)
			.opts(opts)
			.pageNum(request.getPage_num())
			.build();

		fairytale.addOriginalCharacter(originalCharacter);

		return originalCharacterRepository.save(originalCharacter);
	}

	@Transactional
	@Override
	public void swapAllImage(User user) {
		if (user.getFaceImageUrl() == null || user.getOpts() == null)
			throw new UserFaceNotExistException(ErrorStatus._USER_FACE_NOT_EXIST);
		for (Fairytale fairytale : fairytaleRepository.findAll()) {
			for (OriginalCharacter originalCharacter : fairytale.getOriginalCharacterList()) {
				FaceSwapRequestDto.FaceSwapRequest request = FaceSwapRequestDto.FaceSwapRequest.builder()
					.sourceImage(FaceSwapRequestDto.SourceImage.builder()
						.sourceOpts(user.getOpts())
						.sourcePath(user.getFaceImageUrl())
						.build())
					.targetImage(FaceSwapRequestDto.TargetImage.builder()
						.targetOpts(originalCharacter.getOpts())
						.targetPath(originalCharacter.getOriginalURL())
						.build())
					.modifyImage(originalCharacter.getOriginalURL())
					.webhookUrl(WEBHOOK_URL)
					.build();

				addSaveQueue(request, user, fairytale, originalCharacter.getPageNum());
			}
		}
	}

	@Override
	@Transactional
	public void addSaveQueue(FaceSwapRequestDto.FaceSwapRequest request, User user, Fairytale fairytale, Long pageNum) {
		SwapResult result = getFaceSwapImg(request);

		ImageSaveQueue imageSaveQueue = ImageSaveQueue.builder()
			.imageURL(result.getUrl())
			.userId(user.getId())
			.fairytaleId(fairytale.getId())
			.pageNum(pageNum)
			.requestId(result.getId())
			.build();

		imageSaveQueueRepository.save(imageSaveQueue);
	}

	@Override
	public SwapResult getFaceSwapImg(FaceSwapRequestDto.FaceSwapRequest faceSwapRequest) {

		OkHttpClient client = new OkHttpClient().newBuilder()
			.build();

		MediaType mediaType = MediaType.parse("application/json");

		String requestString = "{\n" +
			"    \"sourceImage\": [\n" +
			"        {\n" +
			"            \"path\": \"" + faceSwapRequest.getSourceImage().getSourcePath() + "\",\n" +
			"            \"opts\": \"" + faceSwapRequest.getSourceImage().getSourceOpts() + "\"\n" +
			"        }\n" +
			"    ],\n" +
			"    \"targetImage\": [\n" +
			"        {\n" +
			"            \"path\": \"" + faceSwapRequest.getTargetImage().getTargetPath() + "\",\n" +
			"            \"opts\": \"" + faceSwapRequest.getTargetImage().getTargetOpts() + "\"\n" +
			"        }\n" +
			"    ],\n" +
			"    \"modifyImage\": \"" + faceSwapRequest.getModifyImage() + "\",\n" +
			"    \"webhookUrl\": \"" + faceSwapRequest.getWebhookUrl() + "\"\n" +
			"}";

		LOGGER.info("requestString = {}", requestString);

		RequestBody body = RequestBody.create(mediaType, requestString);

		Request request = new Request.Builder()
			.url("https://openapi.akool.com/api/open/v3/faceswap/highquality/specifyimage")
			.method("POST", body)
			.addHeader("Authorization", "Bearer " + getToken())
			.addHeader("Content-Type", "application/json")
			.build();

		try (Response response = client.newCall(request).execute()) {
			if (!response.isSuccessful()) {
				throw new IOException("Unexpected code " + response);
			}

			String responseData = response.body().string();

			LOGGER.info("responseData = {}", responseData);

			JSONObject jsonObject = new JSONObject(responseData);

			int errCode = jsonObject.getInt("code");
			String errorMsg = jsonObject.getString("msg");

			if (errCode != 1000) {
				throw new IOException("Error! \n" +
					"error code : " +
					errCode + "\n" +
					"error massage : " +
					errorMsg + "\n");
			}

			String url = jsonObject.getJSONObject("data").getString("url");
			String id = jsonObject.getJSONObject("data").getString("_id");

			return new SwapResult(id, url);
		} catch (IOException e) {
			LOGGER.error("Face Swap에 실패했습니다.");
			e.printStackTrace();
			throw new FaceSwapFailureException(ErrorStatus._FACE_SWAP_FAILURE_ERROR);
		}
	}

	public String getToken() {

		OkHttpClient client = new OkHttpClient().newBuilder()
			.build();
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType,
			"{\r\n    \"clientId\": \"" + clientId + "\" ,\r\n    \"clientSecret\": \"" + apikey + "\"\r\n}");
		Request request = new Request.Builder()
			.url("https://openapi.akool.com/api/open/v3/getToken")
			.method("POST", body)
			.addHeader("Content-Type", "application/json")
			.build();
		try {
			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();
			JsonNode jsonNode = objectMapper.readTree(responseBody);
			String token = jsonNode.get("token").asText();
			return token;
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("getToken() 요청 중 에러 발생");
			throw new GeneralException(ErrorStatus._REQUEST_FAIL_ERROR);
		}
	}

}

