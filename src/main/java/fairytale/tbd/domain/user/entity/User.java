package fairytale.tbd.domain.user.entity;


import fairytale.tbd.domain.user.enums.Gender;
import fairytale.tbd.domain.voice.entity.Voice;
import fairytale.tbd.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class User extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name = "login_id", nullable = false)
	private String loginId;

	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender", nullable = false)
	private Gender gender;

	@Column(name = "username", nullable = false)
	private String username;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Voice voice;


	// 연관관계 편의 메서드
	public void setVoice(Voice voice){
		this.voice = voice;
		voice.setUser(this);
	}

}
