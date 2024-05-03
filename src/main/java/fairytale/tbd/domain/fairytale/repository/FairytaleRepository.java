package fairytale.tbd.domain.fairytale.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fairytale.tbd.domain.fairytale.entity.Fairytale;

@Repository
public interface FairytaleRepository extends JpaRepository<Fairytale, Long> {
	boolean existsByName(String name);

	Optional<Fairytale> findByName(String name);
}
