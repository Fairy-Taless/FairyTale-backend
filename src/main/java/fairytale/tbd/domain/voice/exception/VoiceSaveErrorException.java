package fairytale.tbd.domain.voice.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class VoiceSaveErrorException extends GeneralException {
	public VoiceSaveErrorException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
