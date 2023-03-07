package blackjack.view;

public class InputValidator {

    private static final String NULL_OR_BLANK_ERROR_MESSAGE = "null 또는 빈칸이 들어올 수 없습니다.";

    public void validateInput(final String input) {
        checkNull(input);
    }

    public void checkNull(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(NULL_OR_BLANK_ERROR_MESSAGE);
        }
    }
}
