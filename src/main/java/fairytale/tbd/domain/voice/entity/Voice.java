package fairytale.tbd.domain.voice.entity;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Voice extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "voice_id")
	private Long id;

	@Column(name = "voice_key_id", nullable = false)
	private String keyId;


	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;


	// 연관 관계 편의 메소드
	public void setUser(User user){
		this.user = user;

	}
}
