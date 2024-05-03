package fairytale.tbd.domain.faceSwap.entity;

import fairytale.tbd.domain.fairytale.entity.Fairytale;
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
    @JoinColumn(name = "fairytale_id")
    private Fairytale fairytaleId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public void setUser(User user){
        this.user = user;
    }

    public void setFairytaleId(Fairytale fairytaleId) {
        this.fairytaleId = fairytaleId;
    }

}
