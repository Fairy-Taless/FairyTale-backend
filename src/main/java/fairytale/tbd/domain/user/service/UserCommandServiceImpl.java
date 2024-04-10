package fairytale.tbd.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fairytale.tbd.domain.user.converter.UserConverter;
import fairytale.tbd.domain.user.entity.User;
import fairytale.tbd.domain.user.repository.UserRepository;
import fairytale.tbd.domain.user.web.dto.UserRequestDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCommandServiceImpl implements UserCommandService{

	private final UserRepository userRepository;

	@Transactional
	@Override
	public User addUser(UserRequestDTO.AddUserDTO request) {
		User user = UserConverter.toUser(request);
		return userRepository.save(user);
	}
}
