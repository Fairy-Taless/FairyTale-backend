package fairytale.tbd.domain.fairytale.service;

import java.util.Optional;

import fairytale.tbd.domain.fairytale.entity.Fairytale;

public interface FairytaleQueryService {
	Optional<Fairytale> getFairytaleById(Long fairytaleId);
}
