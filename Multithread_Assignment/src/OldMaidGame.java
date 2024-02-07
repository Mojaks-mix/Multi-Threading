import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;

public class OldMaidGame extends CardGame{
    private static OldMaidGame instance;
    private final ReentrantLock lock;
    private final Condition nextPlayer;

    private OldMaidGame(int numPlayers, int numDecks) {
        super(numPlayers, new ArrayList<>(numPlayers), new DecksFactory().createDeck(numDecks));
        this.lock = new ReentrantLock();
        this.nextPlayer = lock.newCondition();
    }

    public static OldMaidGame getInstance(int numPlayers, int numDecks) {
        if (instance == null) {
            instance = new OldMaidGame(numPlayers, numDecks);
        }
        return instance;
    }

    public void distributeCards() {
        int numCardsPerPlayer = deck.size() / numPlayers;
        int remainingCards = deck.size() % numPlayers;

        int startIndex = 0;
        for (int i = 0; i < numPlayers; i++) {
            int numCards = numCardsPerPlayer + (i < remainingCards ? 1 : 0);
            List<Card> playerHand = deck.subList(startIndex, startIndex + numCards);
            players.get(i).getHand().addAll(playerHand);
            startIndex += numCards;
        }
    }

    private void initializePlayers() {
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new ComputerPlayer("Player " + i, lock, nextPlayer, players));
        }
    }

    public static boolean isGameOver() {
        for (Player player : players) {
            if (player.isLoser()) {
                for (Player otherPlayer : players) {
                    if (otherPlayer != player && !otherPlayer.getHand().isEmpty()) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public void printResult(){
        for (Player player : players) {
            System.out.println(player.getPlayerName() + " hand: " + player.getHand());
        }
    }

    public void startGame() {
        initializePlayers();

        shuffleDeck();

        distributeCards();

        ArrayList<Thread> playerThreads = new ArrayList<>();

        for (Player player : players) {
            Thread thread = new Thread(player::play);
            playerThreads.add(thread);
            thread.start();
        }

        // Wait for all player threads to finish
        for (Thread thread : playerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Print hands of players after all threads have finished
        printResult();
    }

}