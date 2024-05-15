package fairytale.tbd.domain.faceSwap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fairytale.tbd.domain.faceSwap.entity.ImageSaveQueue;

@Repository
public interface ImageSaveQueueRepository extends JpaRepository<ImageSaveQueue, Long> {
	Optional<ImageSaveQueue> findFirstByOrderByCreatedAtAsc();

}
