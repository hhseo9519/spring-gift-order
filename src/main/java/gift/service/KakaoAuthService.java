package gift.service;

import gift.config.KakaoProperties;
import gift.dto.KakaoTokenRequestDto;
import gift.dto.KakaoTokenResponseDto;
import gift.dto.KakaoUserResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class KakaoAuthService {

    private final MemberService memberService;
    private final KakaoProperties kakaoProperties;
    private final RestClient restClient;

    public KakaoAuthService(MemberService memberService, KakaoProperties kakaoProperties) {
        this.memberService = memberService;
        this.kakaoProperties = kakaoProperties;
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(5000);

        this.restClient = RestClient.builder()
                .requestFactory(factory)
                .build();
    }

    public KakaoTokenResponseDto requestAccessToken(String code) {
        String clientId = kakaoProperties.getRegistration().getKakao().getClientId();
        String redirectUri = kakaoProperties.getRegistration().getKakao().getRedirectUri();
        String tokenUri = kakaoProperties.getProvider().getKakao().getTokenUri();

        KakaoTokenRequestDto requestDto = new KakaoTokenRequestDto(clientId, redirectUri, code);

        return restClient.post()
                .uri(tokenUri)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(requestDto.FormingData())
                .retrieve()
                .body(KakaoTokenResponseDto.class);
    }


    private KakaoUserResponseDto requestUserInfo(String accessToken) {
        String userInfoUri = kakaoProperties.getProvider().getKakao().getUserInfoUri();

        return restClient.get()
                .uri(userInfoUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(KakaoUserResponseDto.class);
    }

    public String loginWithKakao(String accessToken) {
        KakaoUserResponseDto userInfo = requestUserInfo(accessToken);

        return memberService.loginWithKakao(userInfo, accessToken);

    }
}

