package fairytale.tbd.domain.voice.entity;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "user_text_to_speech_segment")
public class UserTTSSegment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uset_text_to_speech_segment_id")
	private Long id;

	@Column(name = "user_text_to_speech_segment_url")
	private String url;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fairytale_segment_id")
	private Segment segment;

	// 연관 관계 편의 메서드
	public void setUser(User user) {
		this.user = user;
	}

	public void setSegment(Segment segment) {
		this.segment = segment;
	}
}
