package blackjack.util;

import java.util.List;

public enum WinningResult {

    WIN("승"),
    LOSE("패"),
    PUSH("무"),
    ;

    final String name;

    WinningResult(final String name) {
        this.name = name;
    }

    public int getWinCount(final List<WinningResult> dealerResult) {
        return (int) dealerResult.stream()
                .filter(result -> result.equals(WIN))
                .count();
    }

    public int getLoseCount(final List<WinningResult> dealerResult) {
        return (int) dealerResult.stream()
                .filter(result -> result.equals(LOSE))
                .count();
    }

    public int getPushCount(final List<WinningResult> dealerResult) {
        return (int) dealerResult.stream()
                .filter(result -> result.equals(PUSH))
                .count();
    }

    public String getName() {
        return name;
    }
}
