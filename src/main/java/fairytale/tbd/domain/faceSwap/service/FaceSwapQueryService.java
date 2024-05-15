package fairytale.tbd.domain.faceSwap.service;

import java.util.Map;

import fairytale.tbd.domain.user.entity.User;

public interface FaceSwapQueryService {
	Map<Long, String> getFaceImages(User user, Long fairytaleId, boolean changeFace);
}
