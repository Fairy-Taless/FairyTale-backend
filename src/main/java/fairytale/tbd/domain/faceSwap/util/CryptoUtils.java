package fairytale.tbd.domain.faceSwap.util;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fairytale.tbd.domain.faceSwap.web.dto.WebhookRequestDTO;

@Component
public class CryptoUtils {

	@Value("${face.akool.apikey}")
	private String clientSecret;

	@Value("${face.akool.clientId}")
	private String clientId;


	// Generate signature
	public static String generateMsgSignature(String clientId, String timestamp, String nonce, String msgEncrypt) {
		String[] arr = {clientId, timestamp, nonce, msgEncrypt};
		Arrays.sort(arr);
		String sortedStr = String.join("", arr);
		return sha1(sortedStr);
	}

	// SHA-1 hash function
	private static String sha1(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] hashBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
			return DatatypeConverter.printHexBinary(hashBytes).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Decryption algorithm
	public static String generateAesDecrypt(String dataEncrypt, String clientId, String clientSecret) {
		try {
			byte[] keyBytes = clientSecret.getBytes(StandardCharsets.UTF_8);
			byte[] ivBytes = clientId.getBytes(StandardCharsets.UTF_8);

			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

			byte[] encryptedBytes = DatatypeConverter.parseHexBinary(dataEncrypt);
			byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

			return new String(decryptedBytes, StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Encryption algorithm
	public static String generateAesEncrypt(String data, String clientId, String clientSecret) {
		try {
			byte[] keyBytes = clientSecret.getBytes(StandardCharsets.UTF_8);
			byte[] ivBytes = clientId.getBytes(StandardCharsets.UTF_8);

			SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
			IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

			byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
			return DatatypeConverter.printHexBinary(encryptedBytes).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Example usage
	public String getURL(WebhookRequestDTO.RequestDTO webhookRequestDTO){
		String newSignature = generateMsgSignature(clientId, webhookRequestDTO.getTimestamp(), webhookRequestDTO.getNonce(), webhookRequestDTO.getDataEncrypt());
		if (webhookRequestDTO.getSignature().equals(newSignature)) {
			String result = generateAesDecrypt(webhookRequestDTO.getDataEncrypt(), clientId, clientSecret);
			return result;
		}
		return null;
	}
}
