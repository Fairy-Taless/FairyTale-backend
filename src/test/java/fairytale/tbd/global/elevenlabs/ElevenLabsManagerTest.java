package fairytale.tbd.global.elevenlabs;

import java.io.File;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ElevenLabsManagerTest {

	@Autowired
	ElevenlabsManager elevenlabsManager;


	@Test
	void ttsTest(){
		String voiceId = "sCGuNWRaRQXJYQ4xoCLG";
		File file = elevenlabsManager.elevenLabsTTS("안녕하세요 toTTS 테스트입니다.", voiceId);
		Assertions.assertThat(file).exists();
	}
}
