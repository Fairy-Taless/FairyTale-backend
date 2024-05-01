package fairytale.tbd.domain.faceSwap.service;

import fairytale.tbd.domain.faceSwap.entity.CustomCharacter;
import fairytale.tbd.domain.faceSwap.entity.Uuid;
import fairytale.tbd.domain.faceSwap.repository.CustomCharacterRepository;
import fairytale.tbd.domain.faceSwap.repository.UuidRepository;
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
public class PhotoUploadServiceImpl {

    private final UuidRepository uuidRepository;
    private final CustomCharacterRepository customCharacterRepository;
    private final AmazonS3Manager amazonS3Manager;

    public void savePhotos(MultipartFile file) throws IOException{
        try{
            String uuid = UUID.randomUUID().toString();

            Uuid saveUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());

            byte[] imageData = file.getBytes();

            String imgURL = amazonS3Manager.uploadFile(saveUuid.getUuid(), file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
