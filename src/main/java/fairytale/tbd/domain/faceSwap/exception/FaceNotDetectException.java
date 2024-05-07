package fairytale.tbd.domain.faceSwap.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class FaceNotDetectException extends GeneralException {
	public FaceNotDetectException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
