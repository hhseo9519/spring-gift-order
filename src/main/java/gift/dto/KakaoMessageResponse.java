package gift.dto;

public class KakaoMessageResponse {
    private int result_code;
    private String msg;

    public boolean isSuccess() {
        return result_code == 0;
    }

    public String getMsg() {
        return msg;
    }
}