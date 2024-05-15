package fairytale.tbd.domain.faceSwap.entity;

import fairytale.tbd.domain.fairytale.entity.Fairytale;
import fairytale.tbd.domain.user.entity.User;
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
@Table(name = "custom_character")
public class CustomCharacter {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "custom_character_id")
	private Long customId;

	@Column(name = "custom_character_image_url", nullable = false)
	private String customURL;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "page_num", nullable = false)
	private Long pageNum;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fairytale_id")
	private Fairytale fairytale;

	public void setUser(User user) {
		this.user = user;
	}

	public void setFairytale(Fairytale fairytale) {
		this.fairytale = fairytale;
	}
}
