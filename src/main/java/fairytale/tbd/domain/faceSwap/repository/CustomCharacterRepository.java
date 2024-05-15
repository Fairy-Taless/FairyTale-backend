package fairytale.tbd.domain.faceSwap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fairytale.tbd.domain.faceSwap.entity.CustomCharacter;

@Repository
public interface CustomCharacterRepository extends JpaRepository<CustomCharacter, Long> {
	List<CustomCharacter> findByUserIdAndFairytaleId(Long userId, Long fairytaleId);
}
