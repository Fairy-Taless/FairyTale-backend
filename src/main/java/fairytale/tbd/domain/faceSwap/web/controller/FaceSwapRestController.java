package fairytale.tbd.domain.faceSwap.web.controller;

import fairytale.tbd.domain.faceSwap.service.FaceDetectApiServiceImpl;
import fairytale.tbd.domain.faceSwap.service.FaceSwapApiServiceImpl;
import fairytale.tbd.domain.faceSwap.service.PhotoUploadServiceImpl;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectResponseDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapResponseDto;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.global.annotation.LoginUser;
import fairytale.tbd.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/faceSwap")
@RequiredArgsConstructor
public class FaceSwapRestController {

    private final PhotoUploadServiceImpl photoUploadService;
    private final FaceDetectApiServiceImpl faceDetectApiService;
    private final FaceSwapApiServiceImpl faceSwapApiService;

    @PostMapping("/uploadImg")
    public ApiResponse<FaceDetectRequestDto> uploadImg(@LoginUser User user, @ModelAttribute MultipartFile file) throws IOException{
        FaceDetectRequestDto faceDetectRequestDto = photoUploadService.savePhotos(user, file);
        log.info("Face detect request : {}", faceDetectRequestDto);
        return ApiResponse.onSuccess(faceDetectRequestDto);
    }

/*    @GetMapping("/createCharacter")
    public ApiResponse<FaceSwapResponseDtoDto> detectFace(FaceDetectRequestDto faceDetectRequestDto) throws IOException {
        FaceDetectResponseDto faceDetectResponseDto = faceDetectApiService.getOptFromFaceDetectApi(faceDetectRequestDto);

        return ApiResponse.onSuccess();
    }*/
}