package fairytale.tbd.domain.user.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class UserNotExistException extends GeneralException {
	public UserNotExistException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
