package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectResponseDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapResponseDto;

public interface FaceSwapApiService {
    /*FaceSwapResponseDto*/void getFaceSwapImg(FaceDetectResponseDto faceDetectResponseDto);
}
