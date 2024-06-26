package fairytale.tbd.global.security.jwt.handler;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import fairytale.tbd.global.enums.statuscode.ErrorStatus;
import fairytale.tbd.global.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
/**
 * JWT 로그인 필터 Failure Handler
 */
public class JwtLoginFailureHandler implements AuthenticationFailureHandler {
	private static final Logger LOGGER = LogManager.getLogger(JwtLoginSuccessHandler.class);
	private final ObjectMapper objectMapper;
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws
		IOException, ServletException {
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		response.setContentType("application/json;charset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.onFailure(ErrorStatus._BAD_REQUEST.getCode(),ErrorStatus._BAD_REQUEST.getMessage(), "로그인에 실패했습니다.")));
		LOGGER.info("Jwt Login fail :: error = {}", exception.getMessage());
	}
}

