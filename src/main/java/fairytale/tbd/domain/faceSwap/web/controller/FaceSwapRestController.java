package fairytale.tbd.domain.faceSwap.web.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import fairytale.tbd.domain.faceSwap.converter.FaceSwapConverter;
import fairytale.tbd.domain.faceSwap.entity.OriginalCharacter;
import fairytale.tbd.domain.faceSwap.service.FaceDetectApiServiceImpl;
import fairytale.tbd.domain.faceSwap.service.FaceSwapApiService;
import fairytale.tbd.domain.faceSwap.service.PhotoUploadServiceImpl;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectResponseDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceRequestDTO;
import fairytale.tbd.domain.faceSwap.web.dto.FaceResponseDTO;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.WebhookRequestDTO;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.service.UserCommandService;
import fairytale.tbd.global.annotation.LoginUser;
import fairytale.tbd.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/faceSwap")
@RequiredArgsConstructor
public class FaceSwapRestController {

	private final Logger LOGGER = LogManager.getLogger(FaceSwapRestController.class);
	private final PhotoUploadServiceImpl photoUploadService;
	private final FaceDetectApiServiceImpl faceDetectApiService;
	private final FaceSwapApiService faceSwapApiService;
	private final UserCommandService userCommandService;

	@PostMapping("/uploadImg")
	public ApiResponse<String> uploadImg(@LoginUser User user, @ModelAttribute MultipartFile file) throws
		IOException {
		FaceDetectRequestDto faceDetectRequestDto = photoUploadService.savePhotos(user, file);
		String imgURL = faceDetectRequestDto.getImgURL();

		FaceDetectRequestDto requestDto = new FaceDetectRequestDto(imgURL);

		FaceDetectResponseDto optFromFaceDetectApi = faceDetectApiService.getOptFromFaceDetectApi(requestDto);

		userCommandService.setUserImageOpts(optFromFaceDetectApi.getOpt(), user);
		userCommandService.setUserImageUrl(optFromFaceDetectApi.getPhotoUrl(), user);

		faceSwapApiService.swapAllImage(user);
		return ApiResponse.onSuccess("이미지 업로드 요청을 성공했습니다.");
	}

	public ApiResponse<FaceDetectResponseDto> test(@RequestBody String faceDetectRequestDto) {

		LOGGER.info("Face detect request : {}", faceDetectRequestDto);

		FaceDetectRequestDto requestDto = new FaceDetectRequestDto(faceDetectRequestDto);

		FaceDetectResponseDto optFromFaceDetectApi = null;

		try {
			optFromFaceDetectApi = faceDetectApiService.getOptFromFaceDetectApi(requestDto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ApiResponse.onSuccess(optFromFaceDetectApi);
	}

	@PostMapping("/originalCharacter")
	public ApiResponse<FaceResponseDTO.OriginalCharacterSaveResponseDTO> originalCharacter(@ModelAttribute
	FaceRequestDTO.OriginalCharacterSaveRequestDTO request) {
		OriginalCharacter originalCharacter = faceSwapApiService.saveOriginalCharacter(request);
		FaceResponseDTO.OriginalCharacterSaveResponseDTO responseDTO = FaceSwapConverter.toOriginalCharacterSaveResponseDTO(
			originalCharacter);
		return ApiResponse.onSuccess(responseDTO);
	}

	// @PostMapping("/swapUserFace")
	public ApiResponse<Map<String, String>> swapUserFace(
		@RequestBody FaceSwapRequestDto.FaceSwapRequest faceSwapRequestDto, @LoginUser User user) {
		// String test = faceSwapApiService.getFaceSwapImg(faceSwapRequestDto);

		return null;
	}

	@PostMapping("/webhook")
	public ResponseEntity<?> webhook(@RequestBody WebhookRequestDTO.RequestDTO request) {
		LOGGER.info("request = {}", request);
		photoUploadService.saveCustomCharacter();
		return ResponseEntity.ok().build();
	}
}
