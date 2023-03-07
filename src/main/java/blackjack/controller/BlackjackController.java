package blackjack.controller;

import blackjack.domain.*;
import blackjack.util.CardPickerGenerator;
import blackjack.util.RandomCardPickerGenerator;
import blackjack.view.InputView;
import blackjack.view.OutputView;

import java.util.List;

public class BlackjackController {

    private final InputView inputView;
    private final OutputView outputView;

    public BlackjackController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        List<String> playersName = inputPlayerNameCommand();
        Cards cards = Cards.create(new RandomCardPickerGenerator());
        Participants participants = Participants.from(playersName);
        BlackjackGame blackjackGame = new BlackjackGame(participants, cards);
        gameSetting(participants, blackjackGame);
        hitParticipantsCard(blackjackGame, cards);
        printResult(participants, blackjackGame);
    }

    private List<String> inputPlayerNameCommand() {
        try {
            return inputView.readPlayerName();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return inputPlayerNameCommand();
    }

    private String inputHitCommand(final Player player) {
        try {
            return inputView.readHitCommand(player.getName());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return inputHitCommand(player);
    }

    private void hitParticipantsCard(final BlackjackGame blackjackGame, final Cards cards) {
        List<Player> players = blackjackGame.findPlayers();
        for (Player player : players) {
            hitPlayerCard(player, cards);
        }
        blackjackGame.dealerHitCard(cards);
        outputView.printHitDealerCount(blackjackGame.findDealer());
    }

    private void printResult(final Participants participants, final BlackjackGame blackjackGame) {
        for(Participant participant : participants.getParticipants()) {
            outputView.printTotalCardsAndScore(participant);
        }
        outputView.printAllWinORLose(blackjackGame.generatePlayersResult());
    }

    private void hitPlayerCard(final Player player, final Cards cards) {
        while (player.decideHit() && HitCommand.of(inputHitCommand(player)).isQuit()) {
            player.hit(cards.pick());
            outputView.printCurrentCards(player);
        }
        if (player.decideHit()) {
            outputView.printCurrentCards(player);
        }
    }

    private void gameSetting(final Participants participants, final BlackjackGame blackjackGame) {
        for (Participant participant : participants.getParticipants()) {
            blackjackGame.getTwoHitCards(participant);
        }
        outputView.printParticipants(participants.getParticipantsName());
        outputView.printParticipantsCard(blackjackGame.findDealer(), blackjackGame.findPlayers());
    }
}
