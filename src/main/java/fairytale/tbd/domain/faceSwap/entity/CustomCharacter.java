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
public class CustomCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customId;

    private String customURL;

    @OneToOne
    @JoinColumn(name = "userId")
    private User userId;


    @ManyToOne
    @JoinColumn(name = "fairytale_id")
    private Fairytale fairytaleId;

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public void setFairytaleId(Fairytale fairytaleId) {
        this.fairytaleId = fairytaleId;
    }
}
