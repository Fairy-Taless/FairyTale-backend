package fairytale.tbd.domain.faceSwap.entity;

import fairytale.tbd.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customId;

    private String customURL;

    @OneToOne
    @JoinColumn(name = "userId")
    private User userId;

    @OneToOne
    @JoinColumn(name = "storyBookId")
    private OriginalStoryBook originalStoryBookId;

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setOriginalStoryBookId(OriginalStoryBook originalStoryBookId){
        this.originalStoryBookId = originalStoryBookId;
    }
}
