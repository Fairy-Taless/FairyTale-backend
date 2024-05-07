package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectResponseDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapResponseDto;

import java.io.IOException;
import java.util.Map;

public interface FaceSwapApiService {
    Map<String, String> getFaceSwapImg(FaceSwapRequestDto.FaceSwapRequest faceSwapRequestDto) throws IOException;
}
