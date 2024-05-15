package fairytale.tbd.domain.voice.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class InvalidVoiceTypeException extends GeneralException {
	public InvalidVoiceTypeException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
