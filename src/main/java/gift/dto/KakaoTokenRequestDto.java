package gift.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class KakaoTokenRequestDto {
    private final String grant_type = "authorization_code";
    private final String client_id;
    private final String redirect_uri;
    private final String code;

    public KakaoTokenRequestDto(String client_id, String redirect_uri, String code) {
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.code = code;
    }

    public MultiValueMap<String, String> FormingData() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", grant_type);
        form.add("client_id", client_id);
        form.add("redirect_uri", redirect_uri);
        form.add("code", code);
        return form;
    }
}
