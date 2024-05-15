package fairytale.tbd.global.elevenlabs.exception;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class FileConvertException extends GeneralException {
	public FileConvertException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
