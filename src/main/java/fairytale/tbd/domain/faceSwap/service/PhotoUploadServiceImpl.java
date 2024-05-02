package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.entity.Uuid;
import fairytale.tbd.domain.faceSwap.repository.UuidRepository;
import fairytale.tbd.domain.faceSwap.web.dto.FaceDetectRequestDto;
import fairytale.tbd.global.aws.s3.AmazonS3Manager;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoUploadServiceImpl implements PhotoUploadService {

    private final UuidRepository uuidRepository;
    private final AmazonS3Manager amazonS3Manager;

    @Override
    @Transactional
    public FaceDetectRequestDto savePhotos(MultipartFile file) throws IOException {
        String imgURL = "";
        String uuid = UUID.randomUUID().toString();

        try {
            Uuid saveUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
            byte[] imageData = file.getBytes();
            imgURL = amazonS3Manager.uploadFile(saveUuid.getUuid(), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FaceDetectRequestDto faceDetectRequestDto = new FaceDetectRequestDto();
        faceDetectRequestDto.setImgURL(imgURL);

        return faceDetectRequestDto;
    }

}
