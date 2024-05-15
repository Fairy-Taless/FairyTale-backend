package fairytale.tbd.domain.faceSwap.service;

import java.io.IOException;

import fairytale.tbd.domain.faceSwap.entity.OriginalCharacter;
import fairytale.tbd.domain.faceSwap.util.SwapResult;
import fairytale.tbd.domain.faceSwap.web.dto.FaceRequestDTO;
import fairytale.tbd.domain.faceSwap.web.dto.FaceSwapRequestDto;
import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.user.entity.User;

public interface FaceSwapApiService {
	SwapResult getFaceSwapImg(FaceSwapRequestDto.FaceSwapRequest faceSwapRequestDto) throws IOException;

	void addSaveQueue(FaceSwapRequestDto.FaceSwapRequest request, User user, Fairytale fairytale, Long pageNum);

	void swapAllImage(User user);

	OriginalCharacter saveOriginalCharacter(FaceRequestDTO.OriginalCharacterSaveRequestDTO request);
}
