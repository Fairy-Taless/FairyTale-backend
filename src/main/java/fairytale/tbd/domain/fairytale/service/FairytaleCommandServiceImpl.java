package fairytale.tbd.domain.fairytale.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fairytale.tbd.domain.fairytale.converter.Fairytaleconverter;
import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.fairytale.repository.FairytaleRepository;
import fairytale.tbd.domain.fairytale.web.dto.FairytaleRequestDTO;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import fairytale.tbd.global.exception.GeneralException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FairytaleCommandServiceImpl implements FairytaleCommandService {
	private final FairytaleRepository fairytaleRepository;

	/**
	 * 동화 추가 서비스 로직
	 */
	@Override
	@Transactional
	public Fairytale saveFairytale(FairytaleRequestDTO.AddFairytaleRequestDTO request) {
		// TODO BEAN VALIDATION
		if (fairytaleRepository.existsByName(request.getName())) {
			throw new GeneralException(ErrorStatus._FAIRYTALE_EXIST_ERROR);
		}
		Fairytale fairytale = Fairytaleconverter.toFairytale(request);
		return fairytaleRepository.save(fairytale);
	}

}
