package fairytale.tbd.domain.voice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.voice.entity.Segment;
import fairytale.tbd.domain.voice.entity.UserTTSSegment;

@Repository
public interface UserTTSSegmentRepository extends JpaRepository<UserTTSSegment, Long> {
	Optional<UserTTSSegment> findByUserAndSegment(User user, Segment segment);
}
