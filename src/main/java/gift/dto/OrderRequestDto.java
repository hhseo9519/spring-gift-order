package gift.dto;

import jakarta.validation.constraints.*;

public record OrderRequestDto(
        @NotNull(message = "옵션 ID는 필수입니다.")
        Long optionId,

        @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
        int quantity,

        @Size(max = 200, message = "메시지는 최대 200자까지 입력할 수 있습니다.")
        String message
) {}
