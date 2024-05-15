package fairytale.tbd.domain.faceSwap.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fairytale.tbd.domain.faceSwap.entity.CustomCharacter;
import fairytale.tbd.domain.faceSwap.entity.OriginalCharacter;
import fairytale.tbd.domain.faceSwap.repository.CustomCharacterRepository;
import fairytale.tbd.domain.faceSwap.repository.OriginalCharacterRepository;
import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.fairytale.exception.FairytaleNotFoundException;
import fairytale.tbd.domain.fairytale.repository.FairytaleRepository;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FaceSwapQueryServiceImpl implements FaceSwapQueryService {
	private final FairytaleRepository fairytaleRepository;
	private final OriginalCharacterRepository originalCharacterRepository;
	private final CustomCharacterRepository customCharacterRepository;

	@Override
	public Map<Long, String> getFaceImages(User user, Long fairytaleId, boolean changeFace) {
		Map<Long, String> result = new HashMap<>();
		Fairytale fairytale = fairytaleRepository.findById(fairytaleId)
			.orElseThrow(() -> new FairytaleNotFoundException(
				ErrorStatus._FAIRYTALE_NOT_FOUND));

		if (changeFace) {
			for (CustomCharacter customCharacter : customCharacterRepository.findByUserIdAndFairytaleId(user.getId(),
				fairytale.getId())) {
				result.put(customCharacter.getPageNum(), customCharacter.getCustomURL());
			}
		} else {
			for (OriginalCharacter originalCharacter : originalCharacterRepository.findByFairytaleId(
				fairytale.getId())) {
				result.put(originalCharacter.getPageNum(), originalCharacter.getOriginalURL());
			}
		}
		return result;
	}
}
