package fairytale.tbd.domain.faceSwap.web.controller;

import fairytale.tbd.domain.faceSwap.service.FaceDetectApiServiceImpl;
import fairytale.tbd.domain.faceSwap.service.FaceSwapApiServiceImpl;
import fairytale.tbd.domain.faceSwap.service.PhotoUploadServiceImpl;
import fairytale.tbd.domain.faceSwap.util.CryptoUtils;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectResponseDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.WebhookRequestDTO;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.global.annotation.LoginUser;
import fairytale.tbd.global.aws.s3.AmazonS3Manager;
import fairytale.tbd.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/faceSwap")
@RequiredArgsConstructor
public class FaceSwapRestController {

    private final Logger LOGGER = LogManager.getLogger(FaceSwapRestController.class);
    private final PhotoUploadServiceImpl photoUploadService;
    private final FaceDetectApiServiceImpl faceDetectApiService;
    private final FaceSwapApiServiceImpl faceSwapApiService;
    private final CryptoUtils cryptoUtils;

    @PostMapping("/uploadImg")
    public ApiResponse<FaceDetectResponseDto> uploadImg(@LoginUser User user, @ModelAttribute MultipartFile file) throws IOException{
        FaceDetectRequestDto faceDetectRequestDto = photoUploadService.savePhotos(user, file);
        String imgURL = faceDetectRequestDto.getImgURL();
        return test(imgURL);
    }



    public ApiResponse<FaceDetectResponseDto> test(@RequestBody String faceDetectRequestDto){

        LOGGER.info("Face detect request : {}", faceDetectRequestDto);

        FaceDetectRequestDto requestDto = new FaceDetectRequestDto();
        requestDto.setImgURL(faceDetectRequestDto);

        FaceDetectResponseDto optFromFaceDetectApi = null;

        try{
             optFromFaceDetectApi = faceDetectApiService.getOptFromFaceDetectApi(requestDto);
        }
        catch( Exception e){
            e.printStackTrace();
        }


        return ApiResponse.onSuccess(optFromFaceDetectApi);
    }

    @PostMapping("/testSwapApi")
    public ApiResponse<Map<String, String>> test(@RequestBody FaceSwapRequestDto.FaceSwapRequest faceSwapRequestDto){

        Map<String, String> test = new HashMap<>();

        try{
            test = faceSwapApiService.getFaceSwapImg(faceSwapRequestDto);
        }
        catch( Exception e){
            e.printStackTrace();
        }


        return ApiResponse.onSuccess(test);
    }

    @PostMapping("/webhook")
    public void webhook(@RequestBody WebhookRequestDTO.RequestDTO request){
        LOGGER.info("request = {}", request);
    }
}
