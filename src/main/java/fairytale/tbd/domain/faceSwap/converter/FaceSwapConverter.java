package fairytale.tbd.domain.faceSwap.converter;

import java.time.LocalDateTime;

import fairytale.tbd.domain.faceSwap.entity.OriginalCharacter;
import fairytale.tbd.domain.faceSwap.web.dto.FaceResponseDTO;

public class FaceSwapConverter {

	public static FaceResponseDTO.OriginalCharacterSaveResponseDTO toOriginalCharacterSaveResponseDTO(
		OriginalCharacter originalCharacter) {
		return FaceResponseDTO.OriginalCharacterSaveResponseDTO.builder()
			.created_at(LocalDateTime.now())
			.image_url(originalCharacter.getOriginalURL())
			.original_character_id(originalCharacter.getOriginId())
			.build();
	}
}
