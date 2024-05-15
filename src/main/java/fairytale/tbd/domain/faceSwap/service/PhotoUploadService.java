package fairytale.tbd.domain.faceSwap.service;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import fairytale.tbd.domain.faceSwap.entity.ImageSaveQueue;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.user.entity.User;

public interface PhotoUploadService {
	FaceDetectRequestDto savePhotos(User userId, MultipartFile photoUploadRequestDto);

	String migrateToS3(String customImageUrl);

	Optional<ImageSaveQueue> getLastSaveQueueAndDelete();

	void saveCustomCharacter();
}
