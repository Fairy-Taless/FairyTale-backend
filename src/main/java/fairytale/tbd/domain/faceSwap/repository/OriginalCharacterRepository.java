package fairytale.tbd.domain.faceSwap.repository;

import fairytale.tbd.domain.faceSwap.entity.OriginalCharacter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OriginalCharacterRepository extends JpaRepository<OriginalCharacter, Long> {
}
