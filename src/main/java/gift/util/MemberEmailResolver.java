package gift.util;

import gift.dto.KakaoUserResponseDto;

public class MemberEmailResolver {

    public static String resolveEmail(KakaoUserResponseDto userInfo) {
        Long kakaoId = userInfo.getId();

        if (userInfo.getKakaoAccount() != null) {
            String email = userInfo.getKakaoAccount().getEmail();
            if (email != null && !email.isBlank()) {
                return email;
            }
        }

        return "kakao_" + kakaoId + "@kakao.local";
    }
}
