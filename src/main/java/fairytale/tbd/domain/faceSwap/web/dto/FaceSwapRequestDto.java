package fairytale.tbd.domain.faceSwap.web.dto;

import lombok.*;

import java.util.List;

@ToString
public class FaceSwapRequestDto {

    @Getter
    @Setter
    public static class FaceSwapRequest {

        private List<SourceImage> sourceImage;

        private List<TargetImage> targetImage;

        private String modifyImage;

        private String webhookUrl;
    }

    // Original Image
    @Getter
    @Setter
    public static class SourceImage {

        private String sourcePath;

        private String sourceOpts;

    }

    // User Image
    @Getter
    @Setter
    public static class TargetImage {

        private String targetPath;

        private String targetOpts;

    }
}