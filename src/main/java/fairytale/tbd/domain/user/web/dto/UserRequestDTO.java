package fairytale.tbd.domain.user.web.dto;

import fairytale.tbd.domain.user.enums.Gender;
import fairytale.tbd.domain.user.validation.annotation.ExistUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class UserRequestDTO {
	@Getter
	@Setter
	public static class AddUserDTO{

		@Size(min = 4, message = "아이디는 최소 4자 이상이어야 합니다.")
		@NotBlank(message = "LoginId값은 필수입니다.")
		private String loginId;

		@NotBlank(message = "password값은 필수입니다.")
		@Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
		@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()_+={};:,<.>]).{8,}$", message = "비밀번호는 영어, 숫자, 특수문자를 포함해야 합니다.")
		private String password;

		@NotNull(message = "gender값은 필수입니다.")
		private Gender gender;

		@ExistUsername
		@NotBlank(message = "username값은 필수입니다.")
		private String username;

		@Override
		public String toString() {
			return "AddUserDTO{" +
				"loginId='" + loginId + '\'' +
				", password='" + password + '\'' +
				", gender=" + gender +
				", username='" + username + '\'' +
				'}';
		}
	}
}
