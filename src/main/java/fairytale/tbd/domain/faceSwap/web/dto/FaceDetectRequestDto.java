package fairytale.tbd.domain.faceSwap.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FaceDetectRequestDto {

	private String imgURL;

}
