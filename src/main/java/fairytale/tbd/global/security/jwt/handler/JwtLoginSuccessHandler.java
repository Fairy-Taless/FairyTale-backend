package fairytale.tbd.global.security.jwt.handler;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import fairytale.tbd.global.security.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
/**
 * JWT 로그인 필터 Success Handler
 */
public class JwtLoginSuccessHandler implements AuthenticationSuccessHandler {
	private final JwtService jwtService;
	private static final Logger LOGGER = LogManager.getLogger(JwtLoginSuccessHandler.class);

	/**
	 * 인증에 성공하면 Access Token과 Refresh Token을 생성한 후 반환
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		UserDetails principal = (UserDetails)authentication.getPrincipal();
		String loginId = principal.getUsername();
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().write("AUTHENTICATION SUCCESS.");
		LOGGER.info("Jwt Login Success :: Login ID = {}", loginId);
		loginSuccess(response, loginId);
	}

	private void loginSuccess(HttpServletResponse response, String loginId) throws IOException {
		String accessToken = jwtService.createAccessToken(loginId);
		String refreshToken = jwtService.createRefreshToken();
		response.addHeader(jwtService.getAccessHeader(), "Bearer " + accessToken);
		response.addHeader(jwtService.getRefreshHeader(), "Bearer " + refreshToken);

		jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
		jwtService.updateRefreshToken(loginId, refreshToken);
	}
}
