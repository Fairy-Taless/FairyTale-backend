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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/faceSwap")
@RequiredArgsConstructor
public class FaceSwapRestController {

    private final Logger LOGGER = LogManager.getLogger(FaceSwapRestController.class);
    private final PhotoUploadServiceImpl photoUploadService;
    private final FaceDetectApiServiceImpl faceDetectApiService;
    private final FaceSwapApiServiceImpl faceSwapApiService;

    @PostMapping("/uploadImg")
    public ApiResponse<FaceDetectRequestDto> uploadImg(@LoginUser User user, @ModelAttribute MultipartFile file) throws IOException{
        FaceDetectRequestDto faceDetectRequestDto = photoUploadService.savePhotos(user, file);
        return ApiResponse.onSuccess(faceDetectRequestDto);
    }


    @PostMapping("/test")
    public ApiResponse<FaceDetectResponseDto> test(@RequestBody FaceDetectRequestDto faceDetectRequestDto){

        LOGGER.info("Face detect request : {}", faceDetectRequestDto);


        FaceDetectResponseDto optFromFaceDetectApi = null;

        try{
             optFromFaceDetectApi = faceDetectApiService.getOptFromFaceDetectApi(faceDetectRequestDto);
        }
        catch( Exception e){
            e.printStackTrace();
        }


        return ApiResponse.onSuccess(optFromFaceDetectApi);
    }
}