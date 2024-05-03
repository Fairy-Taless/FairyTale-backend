package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.user.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoUploadService {
    FaceDetectRequestDto savePhotos(User userId, MultipartFile photoUploadRequestDto) throws IOException;
}
