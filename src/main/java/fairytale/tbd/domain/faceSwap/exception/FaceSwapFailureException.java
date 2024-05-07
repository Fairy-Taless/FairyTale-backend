package fairytale.tbd.domain.faceSwap.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class FaceSwapFailureException extends GeneralException {
	public FaceSwapFailureException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
