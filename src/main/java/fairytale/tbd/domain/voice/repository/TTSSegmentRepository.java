package fairytale.tbd.domain.voice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fairytale.tbd.domain.voice.entity.TTSSegment;

@Repository
public interface TTSSegmentRepository extends JpaRepository<TTSSegment, Long> {
}
