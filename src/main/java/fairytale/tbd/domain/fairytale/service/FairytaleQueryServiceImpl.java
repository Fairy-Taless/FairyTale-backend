package fairytale.tbd.domain.fairytale.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.fairytale.repository.FairytaleRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FairytaleQueryServiceImpl implements FairytaleQueryService {
	private FairytaleRepository fairytaleRepository;

	@Override
	public Optional<Fairytale> getFairytaleById(Long fairytaleId) {
		return fairytaleRepository.findById(fairytaleId);
	}
}
