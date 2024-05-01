package fairytale.tbd.domain.faceSwap.service;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoUploadService {
    public void savePhotos(MultipartFile file);
}
