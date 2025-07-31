package gift.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.config.KakaoMessageProperties;
import gift.dto.KakaoMessageResponse;
import gift.dto.TextLink;
import gift.dto.TextTemplate;
import gift.entity.Member;
import gift.entity.Order;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class KakaoMessageClient {

    private static final String DEFAULT_MESSAGE_API_PATH = "/v2/api/talk/memo/default/send";
    private static final int TIMEOUT_MILLISECONDS = 5000;

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KakaoMessageClient(KakaoMessageProperties properties) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(TIMEOUT_MILLISECONDS);
        factory.setReadTimeout(TIMEOUT_MILLISECONDS);

        this.restClient = RestClient.builder()
                .baseUrl(properties.getHost())
                .requestFactory(factory)
                .build();
    }

    public void sendOrderMessage(Member member, Order order) {
        String templateJson = createTemplateObject(order);

        KakaoMessageResponse response = restClient.post()
                .uri(DEFAULT_MESSAGE_API_PATH)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .header("Authorization", "Bearer " + member.getKakaoAccessToken())
                .body("template_object=" + URLEncoder.encode(templateJson, StandardCharsets.UTF_8))
                .retrieve()
                .body(KakaoMessageResponse.class);

        if (response == null || !response.isSuccess()) {
            throw new RuntimeException("카카오 메시지 발송 실패: 응답 없음 또는 실패 응답");
        }
    }

    private String createTemplateObject(Order order) {
        String message = String.format(
                "주문이 완료되었습니다.\n옵션: %s\n수량: %d\n메시지: %s",
                order.getOption().getName(),
                order.getQuantity(),
                order.getMessage()
        );

        TextTemplate template = new TextTemplate(
                message,
                new TextLink("http://localhost:8080"),
                "상세보기"
        );

        try {
            return objectMapper.writeValueAsString(template);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("카카오 메시지 JSON 직렬화 실패", e);
        }
    }
}
