package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectResponseDto;

import java.io.IOException;

public interface FaceDetectApiService {
    FaceDetectResponseDto getOptFromFaceDetectApi(FaceDetectRequestDto faceDetectRequestDto) throws IOException;
}
