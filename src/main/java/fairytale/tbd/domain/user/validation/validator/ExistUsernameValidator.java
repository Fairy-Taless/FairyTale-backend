package fairytale.tbd.domain.user.validation.validator;

import fairytale.tbd.domain.user.repository.UserRepository;
import fairytale.tbd.domain.user.validation.annotation.ExistUsername;
import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExistUsernameValidator implements ConstraintValidator<ExistUsername, String> {

	private UserRepository userRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = !userRepository.existsByUsername(value);
		if(!isValid){
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(ErrorStatus._EXIST_USERNAME.getMessage().toString());
		}
		return isValid;
	}

	@Override
	public void initialize(ExistUsername constraintAnnotation) {
		ConstraintValidator.super.initialize(constraintAnnotation);
	}
}
