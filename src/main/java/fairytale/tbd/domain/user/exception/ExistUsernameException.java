package fairytale.tbd.domain.user.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class ExistUsernameException extends GeneralException {
	public ExistUsernameException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
