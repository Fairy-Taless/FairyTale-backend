package fairytale.tbd.domain.voice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.fairytale.exception.FairytaleNotFoundException;
import fairytale.tbd.domain.fairytale.repository.FairytaleRepository;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.voice.entity.Segment;
import fairytale.tbd.domain.voice.entity.TTSSegment;
import fairytale.tbd.domain.voice.entity.UserTTSSegment;
import fairytale.tbd.domain.voice.repository.TTSSegmentRepository;
import fairytale.tbd.domain.voice.repository.UserTTSSegmentRepository;
import fairytale.tbd.domain.voice.web.dto.VoiceResponseDTO;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoiceQueryServiceImpl implements VoiceQueryService {

	private static final Logger LOGGER = LogManager.getLogger(VoiceQueryServiceImpl.class);

	private final VoiceCommandService voiceCommandService;
	private final FairytaleRepository fairytaleRepository;
	private final UserTTSSegmentRepository userTTSSegmentRepository;
	private final TTSSegmentRepository ttsSegmentRepository;

	/**
	 * 특정 사용자의 목소리로 변경한 음성과 기본 모든 음성을 가져옴
	 * 동화의 문장 목록들을 탐색하며
	 * 주인공이라면 사용자의 음성을 (만약 존재하지 않는다면 생성 후 저장)
	 * 주인공이 아니라면 기본 음성을 (마찬가지로 존재하지 않는다면 생성 후 저장)
	 * 받은 후 문장의 페이지 번호를 키값으로 Map 형태로 반환 (문장 순서대로 정렬)
	 */
	@Transactional
	@Override
	public Map<Long, List<VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO>> getUserTTSSegmentList(User user,
		String fairytaleName) {
		// 동화 이름이 유효한지 검증
		// TODO Bean Validation으로 넘기기
		Fairytale fairytale = fairytaleRepository.findByName(fairytaleName)
			.orElseThrow(() -> new FairytaleNotFoundException(ErrorStatus._FAIRYTALE_NOT_FOUND));

		// 동화의 문장 목록을 불러온 후, 페이지 번호, 문장 번호에 대해 오름차순 정렬
		List<Segment> segmentList = fairytale.getSegmentList();
		Collections.sort(segmentList);

		Map<Long, List<VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO>> result = new HashMap<>();

		for (Segment segment : segmentList) {
			VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO resultDTO = null;
			// TODO 상속관계로 변경, ASYNC
			if (segment.isMainCharacter()) {
				UserTTSSegment userTTSSegment = userTTSSegmentRepository.findByUserAndSegment(user, segment)
					.orElseGet(() -> voiceCommandService.saveUserTTSSegment(user, segment));

				resultDTO = VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO.builder()
					.segmentId(segment.getId())
					.audioUrl(userTTSSegment.getUrl())
					.build();
			} else {

				TTSSegment ttsSegment = ttsSegmentRepository.findBySegmentId(segment.getId())
					.orElseGet(() -> voiceCommandService.saveTTSSegment(segment));

				resultDTO = VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO.builder()
					.segmentId(segment.getId())
					.audioUrl(ttsSegment.getUrl())
					.build();
			}
			if (resultDTO == null) {
				LOGGER.error("::: 음성 목록을 가져오는 중 에러 발생 :::");
				throw new RuntimeException("음성 목록을 가져오는 중 에러가 발생했습니다.");
			}
			if (!result.containsKey(segment.getPageNum())) {
				result.put(segment.getPageNum(), new ArrayList<VoiceResponseDTO.GetUserTTSSegmentResultDetailDTO>());
			}
			result.get(segment.getPageNum()).add(resultDTO);
		}
		return result;
	}

}
