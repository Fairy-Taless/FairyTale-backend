package fairytale.tbd.domain.faceSwap.web.controller;

import fairytale.tbd.domain.faceSwap.service.PhotoUploadServiceImpl;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.PhotoUploadRequestDto;
import fairytale.tbd.domain.user.entity.User;
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

    @PostMapping("/uploadImg")
    public ApiResponse<FaceDetectRequestDto> uploadImg(/*@LoginUser User user,*/ @ModelAttribute MultipartFile photoUploadRequestDto) throws IOException{
        FaceDetectRequestDto faceDetectRequestDto = photoUploadService.savePhotos(photoUploadRequestDto);
        log.info("Face detect request : {}", faceDetectRequestDto);
        return ApiResponse.onSuccess(faceDetectRequestDto);
    }
}