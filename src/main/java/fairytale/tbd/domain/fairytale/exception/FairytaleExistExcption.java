package fairytale.tbd.domain.fairytale.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class FairytaleExistExcption extends GeneralException {
	public FairytaleExistExcption(BaseCode errorStatus) {
		super(errorStatus);
	}
}
