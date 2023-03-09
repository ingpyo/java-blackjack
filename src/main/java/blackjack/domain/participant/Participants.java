package blackjack.domain.participant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Participants {

    private static final String DEALER_NAME = "딜러";

    private final List<Participant> participants;

    private Participants(final List<Participant> participants) {
        this.participants = participants;
    }

    public static Participants from(final List<String> playersName,final Dealer dealer) {
        List<Participant> participants = new ArrayList<>();
        participants.add(dealer);
        for (String playerName : playersName) {
            participants.add(new Player(new ParticipantName(playerName)));
        }
        return new Participants(participants);
    }

    public List<Player> findPlayers() {
        return getParticipants().stream()
                .filter(participant -> !participant.getParticipantName().equals(new ParticipantName(DEALER_NAME)))
                .map(it -> (Player) it)
                .collect(Collectors.toList());
    }

    public Dealer findDealer() {
        return (Dealer) getParticipants().stream()
                .filter(participant -> participant.getParticipantName().equals(new ParticipantName(DEALER_NAME)))
                .findFirst()
                .get();
    }

    public List<String> getParticipantsName() {
        return participants.stream()
            .map(participant -> participant.getParticipantName().getName())
            .collect(Collectors.toUnmodifiableList());
    }

    public List<Participant> getParticipants() {
        return Collections.unmodifiableList(participants);
    }
}
