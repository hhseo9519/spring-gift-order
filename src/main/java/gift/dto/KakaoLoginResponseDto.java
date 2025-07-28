package gift.dto;

public class KakaoLoginResponseDto {
    private final String token;

    public KakaoLoginResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
