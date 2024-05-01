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
public class OriginalCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long originId;

    private String originalURL;

    @ManyToOne
    @JoinColumn(name = "storyBookId")
    private OriginalStoryBook originalStoryBook;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public void setOriginalStoryBook(OriginalStoryBook originalStoryBook){
        this.originalStoryBook = originalStoryBook;
    }

    public void setUser(User user){
        this.user = user;
    }
}
