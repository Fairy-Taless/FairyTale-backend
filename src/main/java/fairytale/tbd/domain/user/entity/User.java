package fairytale.tbd.domain.user.entity;

import java.util.ArrayList;
import java.util.List;

import fairytale.tbd.domain.faceSwap.entity.CustomCharacter;
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
import jakarta.persistence.OneToMany;
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

	@Column(name = "refresh_token", nullable = true)
	private String refreshToken;

	@Column(name = "user_face_image_url", nullable = true)
	private String faceImageUrl;

	@Column(name = "user_face_image_opts", nullable = true)
	private String opts;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Voice voice;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Authority> authorityList = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomCharacter> customCharacterList = new ArrayList<>();

	// 연관관계 편의 메서드
	public void setVoice(Voice voice) {
		this.voice = voice;
		voice.setUser(this);
	}

	public void addAuthority(Authority authority) {
		authorityList.add(authority);
		authority.setUser(this);
	}

	public void addCustomCharacter(CustomCharacter customCharacter) {
		customCharacterList.add(customCharacter);
		customCharacter.setUser(this);
	}

	//

	public void setFaceImageUrl(String faceImageUrl) {
		this.faceImageUrl = faceImageUrl;
	}

	public void setOpts(String opts) {
		this.opts = opts;
	}

	// RefreshToken update
	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
