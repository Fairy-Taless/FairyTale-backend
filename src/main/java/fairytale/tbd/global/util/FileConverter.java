package fairytale.tbd.global.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import fairytale.tbd.global.exception.GeneralException;

public class FileConverter {
	public static MultipartFile toMultipartFile(File file, String fileName) {

		MultipartFile multipartFile = null;
		try {
			FileInputStream input = new FileInputStream(file);
			 multipartFile = new MockMultipartFile("file",
				file.getName(),
				"audio/mpeg",
				input);
		}catch (Exception e){
			e.printStackTrace();
			throw new FileConvertException(ErrorStatus._FILE_CONVERT_ERROR);
		}
		return multipartFile;
	}
}
