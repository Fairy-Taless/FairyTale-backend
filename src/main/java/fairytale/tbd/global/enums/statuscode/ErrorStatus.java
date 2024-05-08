package fairytale.tbd.global.enums.statuscode;

import org.springframework.http.HttpStatus;

public enum ErrorStatus implements BaseCode {
	// common
	_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
	_BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
	_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
	_FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

	_REQUEST_FAIL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5001", "외부 API 요청에 실패했습니다."),

	_S3_FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5002", "S3 파일 업로드 중 오류가 발생했습니다."),

	// Fairytale

	_FAIRYTALE_NOT_FOUND(HttpStatus.BAD_REQUEST, "FAIRYTALE4001", "존재하지 않는 동화입니다."),
	_FAIRYTALE_EXIST_ERROR(HttpStatus.BAD_REQUEST, "FAIRYTALE4002", "이미 존재하는 이름의 동화입니다."),
	_FAIRYTALE_DATA_NOT_EXIST(HttpStatus.INTERNAL_SERVER_ERROR, "FAIRYTALE5001", "동화 변환에 실패했습니다. 동화 데이터에 오류가 있습니다."),

	// ElevenLabs
	_FILE_CONVERT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FILE5001", "파일 변환에 실패했습니다."),

	// Face Swap

	_USER_FACE_NOT_EXIST(HttpStatus.BAD_REQUEST, "FACESWAP4002", "사용자가 업로드한 얼굴 이미지가 없습니다."),
	_FACE_NOT_DETECT_ERROR(HttpStatus.BAD_REQUEST, "FACESWAP4001", "이미지에서 인식된 얼굴이 없습니다."),
	_FACE_SWAP_FAILURE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FACESWAP5001", "얼굴 변환에 실패했습니다."),
	_EMPTY_SAVE_QUEUE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FACESWAP5002", "저장 큐가 비어있습니다."),

	// Voice

	_EXIST_VOICE(HttpStatus.BAD_REQUEST, "VOICE4001", "이미 존재하는 목소리입니다."),
	_VOICE_SAVE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "VOICE5002", "목소리 저장에 실패했습니다."),
	_INVALID_VOICE_TYPE(HttpStatus.BAD_REQUEST, "VOICE4002", "올바르지 않은 목소리 종류입니다."),
	_VOICE_NOT_FOUND(HttpStatus.BAD_REQUEST, "VOICE4003", "저장되어 있는 사용자 음성이 없습니다."),
	_USER_TTS_DUPLCATION(HttpStatus.INTERNAL_SERVER_ERROR, "VOICE5003", "변환된 음성에서 중복되는 데이터가 있습니다."),

	// User
	_EXIST_USERNAME(HttpStatus.BAD_REQUEST, "USER4001", "이미 존재하는 닉네임입니다."),
	_USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "존재하지 않는 사용자입니다."),
	_AUTHORIZATION_FAILED(HttpStatus.BAD_REQUEST, "USER4003", "인증에 실패하였습니다.");

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;

	ErrorStatus(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public Integer getStatusValue() {
		return httpStatus.value();
	}
}
