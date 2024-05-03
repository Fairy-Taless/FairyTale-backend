package fairytale.tbd.domain.voice.entity;

import java.util.List;

import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.voice.enums.VoiceType;
import fairytale.tbd.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fairytale_segment")
public class Segment extends BaseEntity implements Comparable<Segment> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fairytale_segment_id")
	private Long id;

	@Column(name = "segment_context", nullable = false)
	private String context;

	@Column(name = "is_main_character", nullable = false)
	private boolean isMainCharacter;

	@Column(name = "voice_type", nullable = false)
	private VoiceType voiceType;

	@Column(name = "segment_num", nullable = false)
	private Double num;

	@Column(name = "page_num", nullable = false)
	private Long pageNum;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fairytale_id", nullable = false)
	private Fairytale fairytale;

	@OneToOne(mappedBy = "segment", cascade = CascadeType.ALL, orphanRemoval = true)
	private TTSSegment ttsSegment;

	@OneToMany(mappedBy = "segment", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<UserTTSSegment> userTTSSegmentList;

	@Override
	public int compareTo(Segment segment) {
		if (this.pageNum == segment.pageNum) {
			if (this.num < segment.num) {
				return -1;
			} else if (this.num == segment.num) {
				return 0;
			} else
				return 1;
		}
		return 1;
	}

	// 연관 관계 편의 메서드

	public void setFairytale(Fairytale fairytale) {
		this.fairytale = fairytale;
	}

	public void setTtsSegment(TTSSegment ttsSegment) {
		this.ttsSegment = ttsSegment;
	}

	public void addUserTTSSegment(UserTTSSegment userTTSSegment) {
		userTTSSegmentList.add(userTTSSegment);
		userTTSSegment.setSegment(this);
	}
}
