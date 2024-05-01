package fairytale.tbd.domain.faceSwap.repository;

import fairytale.tbd.domain.faceSwap.entity.CustomCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomCharacterRepository extends JpaRepository<CustomCharacter, Long> {
}
