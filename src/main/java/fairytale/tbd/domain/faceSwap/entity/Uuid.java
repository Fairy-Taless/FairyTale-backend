package fairytale.tbd.domain.faceSwap.entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

public class Uuid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uuidId;

    @Column(unique = true)
    private String uuid;
}