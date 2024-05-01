package fairytale.tbd.domain.voice.entity;

import fairytale.tbd.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "text_to_speech_segment")
public class TTSSegment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "text_to_speech_segment_id")
	private Long id;

	@Column(name = "text_to_speech_segment_url")
	private String url;

	@Column(name = "history_id")
	private String historyId;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fairytale_segment_id")
	private Segment segment;

	// 연관 관계 편의 메서드

	public void setSegment(Segment segment) {
		this.segment = segment;
	}

}
