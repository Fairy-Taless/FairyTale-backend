package fairytale.tbd.domain.voice.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class VoiceNotFoundException extends GeneralException {
	public VoiceNotFoundException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
