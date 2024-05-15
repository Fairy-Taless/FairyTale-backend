package fairytale.tbd.domain.fairytale.service;

import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.fairytale.web.dto.FairytaleRequestDTO;

public interface FairytaleCommandService {
	Fairytale saveFairytale(FairytaleRequestDTO.AddFairytaleRequestDTO request);
}
