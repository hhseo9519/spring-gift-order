package gift.client;

import gift.entity.Member;
import gift.entity.Order;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class KakaoMessageClient {

    private static final String DEFAULT_MESSAGE_API_PATH = "/v2/api/talk/memo/default/send";

    private final RestClient restClient;

    @Value("${kakao.api.host:https://kapi.kakao.com}")
    private String kakaoApiHost;

    public KakaoMessageClient() {
        this.restClient = RestClient.builder()
                .baseUrl("https://kapi.kakao.com")
                .build();
    }

    public void sendOrderMessage(Member member, Order order) {
        String templateJson = createTemplateObject(order);


            restClient.post()
                    .uri(DEFAULT_MESSAGE_API_PATH)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("Authorization", "Bearer " + member.getKakaoAccessToken())
                    .body("template_object=" + URLEncoder.encode(templateJson, StandardCharsets.UTF_8))
                    .retrieve()
                    .toBodilessEntity();

    }


    private String createTemplateObject(Order order) {
        String escapedText = String.format(
                "주문이 완료되었습니다.\\n옵션: %s\\n수량: %d\\n메시지: %s",
                escapeJson(order.getOption().getName()),
                order.getQuantity(),
                escapeJson(order.getMessage())
        );

        return "{\"object_type\":\"text\",\"text\":\"" + escapedText +
                "\",\"link\":{\"web_url\":\"http://localhost:8080\",\"mobile_web_url\":\"http://localhost:8080\"}," +
                "\"button_title\":\"상세보기\"}";
    }

    private String escapeJson(String text) {
        return text.replace("\"", "\\\"");
    }

}