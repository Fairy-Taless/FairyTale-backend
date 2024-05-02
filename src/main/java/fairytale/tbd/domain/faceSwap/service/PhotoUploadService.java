package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.faceSwap.web.dto.PhotoUploadRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoUploadService {
    FaceDetectRequestDto savePhotos(MultipartFile photoUploadRequestDto) throws IOException;
}
