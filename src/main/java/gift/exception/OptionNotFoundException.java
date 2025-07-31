package gift.exception;
public class OptionNotFoundException extends RuntimeException {
    public OptionNotFoundException() {
        super("해당 옵션이 존재하지 않습니다.");
    }
}


