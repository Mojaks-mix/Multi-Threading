import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.*;

class ComputerPlayer extends Player implements Playable{
    private final ReentrantLock lock;
    private final Condition nextPlayer;
    private final ArrayList<Player> players;

    public ComputerPlayer(String name, ReentrantLock lock, Condition nextPlayer, ArrayList<Player> players) {
        super(name, new ArrayList<>());
        this.lock = lock;
        this.nextPlayer = nextPlayer;
        this.players = players;
    }

    public boolean isLoser(){
        if(hand.size() == 1 && (hand.get(0) instanceof JokerCard))
            return true;
        return false;
    }

    public void discardPair() {
        if(hand.size() > 1){
            Map<String, List<PlayingCard>> cardMap = new HashMap<>();

            // Populate the HashMap with cards based on value and color
            for (Card card : hand) {
                if (card instanceof PlayingCard) {
                    PlayingCard playingCard = (PlayingCard) card;
                    String key = playingCard.getValue() + playingCard.getColor();

                    // If the key is already in the map, add the card to the list
                    cardMap.computeIfAbsent(key, k -> new ArrayList<>()).add(playingCard);
                }
            }

            // Iterate through the map and remove pairs from the original list
            for (List<PlayingCard> cardList : cardMap.values()) {
                if (cardList.size() >= 2) {
                    discardCard(cardList.get(0));
                    discardCard(cardList.get(1));
                }
            }
        }
    }

    public void drawCard() {
        if (players.size() < 2) {
            return;
        }

        int currentPlayerIndex = players.indexOf(this);
        int index = currentPlayerIndex - 1;
        if (index < 0) {
            index = players.size() - 1;
        }

        // Start from the previous player and continue going back until finding a player with cards or until it's the current player
        while (index != currentPlayerIndex && players.get(index).getHand().isEmpty()) {
            index = (index - 1 + players.size()) % players.size();
        }

        if (index != currentPlayerIndex) {
            Player previousPlayer = players.get(index);
            List<Card> previousPlayerHand = previousPlayer.getHand();

            if (!previousPlayerHand.isEmpty()) {
                Random random = new Random();
                int randomCardIndex = random.nextInt(previousPlayerHand.size());
                Card drawnCard = previousPlayerHand.remove(randomCardIndex);

                hand.add(drawnCard);

                System.out.println(name + " drew a card from " + previousPlayer.getPlayerName() + ": " + drawnCard + "\n");
            }
        }
    }

    @Override
    public void discardCard(Card card) {
        hand.remove(card);
    }

    public void play() {
        while (!OldMaidGame.isGameOver()) {
            lock.lock();
            try {
                while (OldMaidGame.currentPlayerIndex != players.indexOf(this)) {
                    nextPlayer.await();
                }

                if (!OldMaidGame.isGameOver() && !hand.isEmpty()) {
                    System.out.println(name + "'s turn. Hand: " + hand);
                    drawCard();
                    discardPair();
                }

                OldMaidGame.currentPlayerIndex = (OldMaidGame.currentPlayerIndex + 1) % players.size();

                nextPlayer.signalAll();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }
}