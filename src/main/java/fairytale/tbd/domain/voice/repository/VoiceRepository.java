package fairytale.tbd.domain.voice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fairytale.tbd.domain.voice.entity.Voice;

@Repository
public interface VoiceRepository extends JpaRepository<Voice, Long> {
	boolean existsVoiceByUserId(Long userId);
}
