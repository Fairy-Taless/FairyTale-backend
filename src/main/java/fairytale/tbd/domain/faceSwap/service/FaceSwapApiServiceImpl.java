package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectResponseDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FaceSwapApiServiceImpl implements FaceSwapApiService{

    // 여기에서 originalCharacter, customCharacter 둘 다에 사용할 수 있게끔 한다.
    @Override
    @Transactional
    public void getFaceSwapImg(FaceDetectResponseDto faceDetectResponseDto){
        FaceSwapRequestDto.FaceSwapRequest faceSwapRequest = new FaceSwapRequestDto.FaceSwapRequest();

    }
}