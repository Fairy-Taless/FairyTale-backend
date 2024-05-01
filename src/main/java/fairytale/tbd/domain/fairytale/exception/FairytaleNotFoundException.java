package fairytale.tbd.domain.fairytale.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class FairytaleNotFoundException extends GeneralException {
	public FairytaleNotFoundException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
