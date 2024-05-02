package fairytale.tbd.global.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class FileConverter {
	public static MultipartFile toMultipartFile(File file, String fileName) throws IOException {
		FileInputStream input = new FileInputStream(file);
		MultipartFile multipartFile = new MockMultipartFile("file",
			file.getName(),
			"audio/mpeg",
			input);
		return multipartFile;
	}
}
