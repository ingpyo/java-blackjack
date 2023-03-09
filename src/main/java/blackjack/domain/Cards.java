package blackjack.domain;

import java.util.ArrayList;
import java.util.List;

public class Cards {

    private List<Card> cards;

    Cards() {
        this.cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getCardSize() {
        return cards.size();
    }

    public boolean hasAceCard() {
        return cards.stream()
                .anyMatch(Card::isAce);
    }

    public int calculateCardNumberAceCardValueOne() {
        return cards.stream()
                .mapToInt(card -> card.getCardNumber().getValue())
                .sum();
    }

    public CardNumber getCardNumberByIndex(final int index) {
        return cards.get(index).getCardNumber();
    }

    public CardSuit getCardSuitByIndex(final int index) {
        return cards.get(index).getCardSuit();
    }
}
