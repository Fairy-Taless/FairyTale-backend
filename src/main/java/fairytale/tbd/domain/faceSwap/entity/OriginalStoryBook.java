package fairytale.tbd.domain.faceSwap.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OriginalStoryBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storyBookId;

    private String title;
}
