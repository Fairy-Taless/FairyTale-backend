package fairytale.tbd.domain.faceSwap.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class UserFaceNotExistException extends GeneralException {
	public UserFaceNotExistException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
