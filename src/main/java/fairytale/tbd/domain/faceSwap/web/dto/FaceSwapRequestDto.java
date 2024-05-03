package fairytale.tbd.domain.faceSwap.web.dto;

import lombok.*;

public class FaceSwapRequestDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FaceSwapRequest {

        private SourceImage sourseImage;

        private TargetImage targetImage;

        // 1 means open, 0 means close
        private int faceEnhance;

        private String modifyImage;

        private String webHookUrl;

    }

    // Original Image
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SourceImage {

        private String sourcePath;

        private String sourceOpts;

    }

    // User Image
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TargetImage {

        private String targetPath;

        private String targetOpts;

    }
}