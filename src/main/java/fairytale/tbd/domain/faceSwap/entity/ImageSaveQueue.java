package fairytale.tbd.domain.faceSwap.entity;

import fairytale.tbd.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image_save_queue")
public class ImageSaveQueue extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_save_id")
	private Long id;

	@Column(name = "user_id", nullable = false)
	private Long userId;

	@Column(name = "image_url", nullable = false)
	private String imageURL;

	@Column(name = "page_num", nullable = false)
	private Long pageNum;

	@Column(name = "fairytale_id", nullable = false)
	private Long fairytaleId;

	@Column(name = "image_swap_request_id", nullable = false)
	private String requestId;
}
