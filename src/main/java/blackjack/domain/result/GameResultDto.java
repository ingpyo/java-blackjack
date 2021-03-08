package blackjack.domain.result;

import blackjack.domain.card.Card;
import java.util.List;

public class GameResultDto {

    private final List<Card> dealerCards;
    private final int dealerSum;
    private final DealerResultDto dealerResult;
    private final List<PlayerResultDto> playersResults;

    public GameResultDto(List<Card> dealerCards, int dealerSum,
            DealerResultDto dealerResult,
            List<PlayerResultDto> playersResults) {
        this.dealerCards = dealerCards;
        this.dealerSum = dealerSum;
        this.dealerResult = dealerResult;
        this.playersResults = playersResults;
    }

    public List<Card> getDealerCards() {
        return dealerCards;
    }

    public int getDealerSum() {
        return dealerSum;
    }

    public DealerResultDto getDealerResult() {
        return dealerResult;
    }

    public List<PlayerResultDto> getPlayersResults() {
        return playersResults;
    }
}
