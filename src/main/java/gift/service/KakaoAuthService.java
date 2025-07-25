package gift.service;

import gift.dto.KakaoTokenResponseDto;
import gift.dto.KakaoUserResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class KakaoAuthService {

    private final MemberService memberService;
    private final RestClient restClient = RestClient.create();

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    public KakaoAuthService(MemberService memberService) {
        this.memberService = memberService;
    }

    public KakaoTokenResponseDto requestAccessToken(String code) {
        return restClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body("grant_type=authorization_code" +
                        "&client_id=" + clientId +
                        "&redirect_uri=" + redirectUri +
                        "&code=" + code)
                .retrieve()
                .body(KakaoTokenResponseDto.class);
    }

    public KakaoUserResponseDto requestUserInfo(String accessToken) {
        return restClient.get()
                .uri(userInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(KakaoUserResponseDto.class);
    }

    public String loginWithKakao(String accessToken) {
        KakaoUserResponseDto userInfo = requestUserInfo(accessToken);
        return memberService.loginWithKakao(userInfo);
    }
}

