package fairytale.tbd.domain.faceSwap.web.controller;

import fairytale.tbd.domain.faceSwap.service.PhotoUploadServiceImpl;
import fairytale.tbd.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/faceSwap")
@RequiredArgsConstructor
public class FaceSwapRestController {

    private final PhotoUploadServiceImpl photoUploadService;

    @PostMapping("/uploadImg")
    public void uploadImg(@RequestParam MultipartFile file) throws IOException{
        photoUploadService.savePhotos(file);
    }
}
