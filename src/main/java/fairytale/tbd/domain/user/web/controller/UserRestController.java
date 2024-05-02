package fairytale.tbd.domain.user.web.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fairytale.tbd.domain.user.converter.UserConverter;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.service.UserCommandService;
import fairytale.tbd.domain.user.web.dto.UserRequestDTO;
import fairytale.tbd.domain.user.web.dto.UserResponseDTO;
import fairytale.tbd.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserRestController {

	private final UserCommandService userCommandService;
	private static final Logger LOGGER = LogManager.getLogger(UserRestController.class);

	@PostMapping("/signup")
	public ApiResponse<UserResponseDTO.AddUserResultDTO> join(@Valid @RequestBody UserRequestDTO.AddUserDTO request) {
		LOGGER.info("request = {}", request);
		User user = userCommandService.addUser(request);
		return ApiResponse.onSuccess(UserConverter.toAddUserResultDTO(user));
	}
}
