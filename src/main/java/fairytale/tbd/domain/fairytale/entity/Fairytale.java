package fairytale.tbd.domain.fairytale.entity;

import java.util.ArrayList;
import java.util.List;

import fairytale.tbd.domain.faceSwap.entity.CustomCharacter;
import fairytale.tbd.domain.faceSwap.entity.OriginalCharacter;
import fairytale.tbd.domain.voice.entity.Segment;
import fairytale.tbd.global.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Fairytale extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fairytale_id")
	private Long id;

	@Column(name = "fairytale_name", nullable = false)
	private String name;

	@OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Segment> segmentList = new ArrayList<>();

	@OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CustomCharacter> customCharacterList = new ArrayList<>();

	@OneToMany(mappedBy = "fairytale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OriginalCharacter> originalCharacterList = new ArrayList<>();



	// 연관 관계 편의 메서드
	public void addSegment(Segment segment) {
		segmentList.add(segment);
		segment.setFairytale(this);
	}

	public void addCustomCharacter(CustomCharacter customCharacter){
		customCharacterList.add(customCharacter);
		customCharacter.setFairytale(this);
	}

	public void addOriginalCharacter(OriginalCharacter originalCharacter){
		originalCharacterList.add(originalCharacter);
		originalCharacter.setFairytale(this);
	}

}
