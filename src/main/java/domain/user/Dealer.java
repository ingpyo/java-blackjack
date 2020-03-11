package domain.user;

import domain.card.Card;

public class Dealer extends User {
    public Card getAnyCard() {
        return this.cards
            .getCards()
            .get(0);
    }
}
