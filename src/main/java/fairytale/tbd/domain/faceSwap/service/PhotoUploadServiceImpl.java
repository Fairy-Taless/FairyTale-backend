package fairytale.tbd.domain.faceSwap.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import fairytale.tbd.domain.faceSwap.entity.CustomCharacter;
import fairytale.tbd.domain.faceSwap.entity.ImageSaveQueue;
import fairytale.tbd.domain.faceSwap.repository.CustomCharacterRepository;
import fairytale.tbd.domain.faceSwap.repository.ImageSaveQueueRepository;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.domain.fairytale.service.FairytaleQueryService;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.service.UserQueryService;
import fairytale.tbd.global.aws.s3.AmazonS3Manager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoUploadServiceImpl implements PhotoUploadService {

	private static final Logger LOGGER = LogManager.getLogger(PhotoUploadServiceImpl.class);

	private final AmazonS3Manager amazonS3Manager;

	private final ImageSaveQueueRepository imageSaveQueueRepository;

	private final UserQueryService userQueryService;

	private final FairytaleQueryService fairytaleQueryService;

	private final CustomCharacterRepository customCharacterRepository;

	@Override
	@Transactional
	public FaceDetectRequestDto savePhotos(User userId, MultipartFile file) {
		String imgURL = "";
		imgURL = amazonS3Manager.uploadFile(amazonS3Manager.generateS3SavePath(amazonS3Manager.FACE_SWAP_USER_PATH),
			file);

		FaceDetectRequestDto faceDetectRequestDto = new FaceDetectRequestDto(imgURL);

		return faceDetectRequestDto;
	}

	@Override
	public String migrateToS3(String customImageUrl) {
		return amazonS3Manager.uploadJpegImageFromUrl(
			amazonS3Manager.generateS3SavePath(amazonS3Manager.FACE_SWAP_USER_PATH), customImageUrl);
	}

	@Override
	@Transactional
	public Optional<ImageSaveQueue> getLastSaveQueueAndDelete() {
		Optional<ImageSaveQueue> imageSaveQueue = imageSaveQueueRepository.findFirstByOrderByCreatedAtAsc();
		imageSaveQueue.ifPresent(saveQueue -> imageSaveQueueRepository.delete(saveQueue));
		return imageSaveQueue;
	}

	@Override
	public void saveCustomCharacter() {
		Optional<ImageSaveQueue> lastSaveQueue = getLastSaveQueueAndDelete();
		lastSaveQueue.ifPresent(imageSaveQueue -> {
			userQueryService.getUserWithUserId(imageSaveQueue.getUserId()).ifPresent(user -> {
				fairytaleQueryService.getFairytaleById(imageSaveQueue.getFairytaleId()).ifPresent(fairytale -> {
					CustomCharacter customCharacter = CustomCharacter.builder()
						.customURL(imageSaveQueue.getImageURL())
						.pageNum(imageSaveQueue.getPageNum())
						.build();

					user.addCustomCharacter(customCharacter);
					fairytale.addCustomCharacter(customCharacter);

					customCharacterRepository.save(customCharacter);
					LOGGER.info("Custom Character 저장 성공");
				});
			});
		});
	}
}
