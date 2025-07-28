package gift.controller;

import gift.config.KakaoProperties;
import gift.dto.KakaoTokenResponseDto;
import gift.dto.KakaoLoginResponseDto;
import gift.service.KakaoAuthService;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class LoginController {

    private final KakaoAuthService kakaoAuthService;
    private final KakaoProperties kakaoProperties;
    private final String kakaoLoginUrl;

    public LoginController(KakaoAuthService kakaoAuthService, KakaoProperties kakaoProperties) {
        this.kakaoAuthService = kakaoAuthService;
        this.kakaoProperties = kakaoProperties;
        this.kakaoLoginUrl = UriComponentsBuilder.fromHttpUrl(
                        kakaoProperties.getProvider().getKakao().getAuthorizationUri())
                .queryParam("client_id", kakaoProperties.getRegistration().getKakao().getClientId())
                .queryParam("redirect_uri", kakaoProperties.getRegistration().getKakao().getRedirectUri())
                .queryParam("response_type", "code")
                .build()
                .toUriString();
    }

    @GetMapping("/login/kakao")
    public void redirectToKakao(HttpServletResponse response) throws IOException {
        response.sendRedirect(kakaoLoginUrl);
    }

    @GetMapping("/oauth/kakao")
    public ResponseEntity<KakaoLoginResponseDto> receiveAuthCode(@RequestParam("code") String code) {
        KakaoTokenResponseDto token = kakaoAuthService.requestAccessToken(code);
        String jwt = kakaoAuthService.loginWithKakao(token.getAccessToken());
        return ResponseEntity.ok(new KakaoLoginResponseDto(jwt));
    }
}
