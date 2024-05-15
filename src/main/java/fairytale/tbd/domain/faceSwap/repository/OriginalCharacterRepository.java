package fairytale.tbd.domain.faceSwap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fairytale.tbd.domain.faceSwap.entity.OriginalCharacter;

@Repository
public interface OriginalCharacterRepository extends JpaRepository<OriginalCharacter, Long> {
	List<OriginalCharacter> findByFairytaleId(Long fairytaleId);
}
