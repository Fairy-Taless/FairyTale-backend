package fairytale.tbd.domain.voice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fairytale.tbd.domain.voice.entity.Segment;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {
}
