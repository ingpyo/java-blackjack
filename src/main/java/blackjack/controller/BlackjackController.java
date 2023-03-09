package blackjack.controller;

import blackjack.domain.*;
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
        Cards cards = Cards.create(new RandomCardPickerGenerator());
        List<String> playersName = inputPlayerNameCommand();
        BlackjackGame blackjackGame = BlackjackGame.of(playersName, cards);
        gameSetting(blackjackGame);
        hitParticipantsCard(blackjackGame);
        BlackjackGameResult blackjackGameResult =
                new BlackjackGameResult(blackjackGame.generatePlayersResult(new BlackJackReferee()));
        printResult(blackjackGame, blackjackGameResult);
    }

    private void gameSetting(final BlackjackGame blackjackGame) {
        for (Participant participant : blackjackGame.getParticipants()) {
            blackjackGame.getTwoHitCards(participant);
        }
        outputView.printParticipants(blackjackGame.getParticipantsName());
        outputView.printParticipantsCard(blackjackGame.findDealer(), blackjackGame.findPlayers());
    }

    private void hitParticipantsCard(final BlackjackGame blackjackGame) {
        List<Player> players = blackjackGame.findPlayers();
        for (Player player : players) {
            hitPlayerCard(player, blackjackGame);
        }
        blackjackGame.hitDealerCard();
        outputView.printHitDealerCount(blackjackGame.findDealer());
    }

    private void hitPlayerCard(final Player player, final BlackjackGame blackjackGame) {
        while (player.decideHit() && HitCommand.of(inputHitCommand(player)).isQuit()) {
            blackjackGame.hitPlayerCard(player);
            outputView.printCurrentCards(player);
        }
    }

    private void printResult(final BlackjackGame blackjackGame, final BlackjackGameResult blackjackGameResult) {
        for (Participant participant : blackjackGame.getParticipants()) {
            outputView.printTotalCardsAndScore(participant);
        }
        outputView.printAllWinORLose(blackjackGameResult);
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
}
