package fairytale.tbd.domain.faceSwap.entity;

import fairytale.tbd.domain.fairytale.entity.Fairytale;
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
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "original_character")
public class OriginalCharacter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "original_character_id")
	private Long originId;

	@Column(name = "originl_character_image_url", nullable = false)
	private String originalURL;

	@Column(name = "page_num", nullable = false)
	private Long pageNum;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fairytale_id")
	private Fairytale fairytale;

	@Column(name = "original_character_image_opts", nullable = false)
	private String opts;

	// 연관 관계 편의 메서드

	public void setFairytale(Fairytale fairytale) {
		this.fairytale = fairytale;
	}

}
