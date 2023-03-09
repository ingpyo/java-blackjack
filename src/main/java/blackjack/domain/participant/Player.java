package blackjack.domain.participant;

public class Player extends Participant {


    private static final int BLACKJACK_MAX_NUMBER = 21;


    public Player(final ParticipantName participantName) {
        super(participantName);
    }

    @Override
    public boolean decideHit() {
        return calculateCardNumber() < BLACKJACK_MAX_NUMBER;
    }
}
