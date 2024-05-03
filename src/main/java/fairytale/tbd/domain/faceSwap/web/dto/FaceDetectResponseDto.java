package fairytale.tbd.domain.faceSwap.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FaceDetectResponseDto {
    private String photoUrl;
    private String opt;
}