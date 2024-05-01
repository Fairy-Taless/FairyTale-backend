package fairytale.tbd.domain.voice.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class ExistVoiceException extends GeneralException {
	public ExistVoiceException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
