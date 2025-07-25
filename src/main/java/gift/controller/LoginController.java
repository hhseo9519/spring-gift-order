package gift.controller;

import gift.dto.KakaoTokenResponseDto;
import gift.dto.LoginResponseDto;
import gift.service.KakaoAuthService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class LoginController {

    private final KakaoAuthService kakaoAuthService;

    public LoginController(KakaoAuthService kakaoAuthService) {
        this.kakaoAuthService = kakaoAuthService;
    }

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.authorization-uri}")
    private String authorizationUri;

    @GetMapping("/login/kakao")
    public void redirectToKakao(HttpServletResponse response) throws IOException {
        String url = UriComponentsBuilder.fromHttpUrl(authorizationUri)
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("response_type", "code")
                .build()
                .toUriString();

        response.sendRedirect(url);
    }

    @GetMapping("/oauth/kakao")
    public ResponseEntity<LoginResponseDto> receiveAuthCode(@RequestParam("code") String code) {
        KakaoTokenResponseDto token = kakaoAuthService.requestAccessToken(code);
        String jwt = kakaoAuthService.loginWithKakao(token.getAccessToken());
        return ResponseEntity.ok(new LoginResponseDto(jwt));
    }
}
