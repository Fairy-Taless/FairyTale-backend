package fairytale.tbd.domain.faceSwap.repository;

import fairytale.tbd.domain.faceSwap.entity.Uuid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UuidRepository extends JpaRepository<Uuid, Long> {
}
