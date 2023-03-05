package blackjack.domain;

import blackjack.util.CardPickerGenerator;

import java.util.*;
import java.util.stream.Collectors;

public class BlackjackGame {

    private static final String DEALER_NAME = "딜러";
    private static final int FIRST_HIT_COUNT = 2;

    private final Participants participants;
    private final Cards cards;

    public BlackjackGame(Participants participants, Cards cards) {
        this.participants = participants;
        this.cards = cards;
    }

    public Map<Player, WinningResult> generatePlayersResult() {
        Map<Player, WinningResult> playersResult = new LinkedHashMap<>();
        Dealer dealer = findDealer();
        List<Player> players = findPlayers();
        for (Player player : players) {
            WinningResult dealerResult = dealer.judgeWinOrLose(player);
            initPlayerResult(playersResult, player, dealerResult);
        }
        return playersResult;
    }

    public List<WinningResult> generateDealerResult() {
        List<WinningResult> dealerResult = new ArrayList<>();
        Dealer dealer = findDealer();
        List<Player> players = findPlayers();
        for (Player player : players) {
            dealerResult.add(dealer.judgeWinOrLose(player));
        }
        return dealerResult;
    }

    public void initFirstHit(CardPickerGenerator cardPickerGenerator) {
        for (Participant participant : participants.getParticipants()) {
            firstHitRule(cardPickerGenerator, participant);
        }
    }
    public void dealerHitCard(final Cards cards, final CardPickerGenerator cardPickerGenerator) {
        Dealer dealer = findDealer();
        while (dealer.decideHit()) {
            dealer.hit(cards.pick(cardPickerGenerator));
        }
    }

    private void initPlayerResult(final Map<Player, WinningResult> playersResult, final Player player, final WinningResult dealerResult) {
        if (dealerResult == WinningResult.WIN) {
            playersResult.put(player, WinningResult.LOSE);
        }
        if (dealerResult == WinningResult.LOSE) {
            playersResult.put(player, WinningResult.WIN);
        }
        if (dealerResult == WinningResult.PUSH) {
            playersResult.put(player, WinningResult.PUSH);
        }
    }

    public List<Player> findPlayers() {
        return participants.getParticipants().stream()
                .filter(participant -> !participant.getParticipantName().equals(new ParticipantName(DEALER_NAME)))
                .map(it -> (Player) it)
                .collect(Collectors.toList());
    }

    public Dealer findDealer() {
        return (Dealer) participants.getParticipants().stream()
            .filter(participant -> participant.getParticipantName().equals(new ParticipantName(DEALER_NAME)))
            .findFirst()
            .get();
    }

    private void firstHitRule(final CardPickerGenerator cardPickerGenerator, final Participant participant) {
        for (int count = 0; count < FIRST_HIT_COUNT; count++) {
            participant.hit(cards.pick(cardPickerGenerator));
        }
    }
}
