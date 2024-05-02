package fairytale.tbd.global.util;

import fairytale.tbd.global.enums.statuscode.BaseCode;
import fairytale.tbd.global.exception.GeneralException;

public class FileConvertException extends GeneralException {
	public FileConvertException(BaseCode errorStatus) {
		super(errorStatus);
	}
}
