import java.util.Arrays;
import java.util.Collections;

/**
 * A standard deck of playing cards with no jokers.
 * @author Anderson Santana
 */
public class Deck
{
  // The number of cards in a deck.
  public static final int NUMBER_OF_CARDS = Card.NUMBER_OF_RANKS * Card.NUMBER_OF_SUITS;
  // The cards in the deck.
  private Card[] cards;
  // The index of the next card to deal.
  private int nextCardIndex = 0;
  
  /**
   * Constructor.
   * Creates a full ordered deck.
   */
  public Deck()
  {
    cards = new Card[NUMBER_OF_CARDS];
    int index = 0;
    for(int suit = Card.NUMBER_OF_SUITS - 1; suit >= 0; suit--)
    {
      for(int rank = Card.NUMBER_OF_RANKS - 1; rank >= 0 ; rank--)
      {
        cards[index++] = new Card(rank, suit);
      }
    }
  }
  
  /**
   * Card accessor.
   * @return The card.
   */
  public Card getCard(int i)
  {
    return cards[i];
  }
    
  /**  
   * Shuffles the deck by using the Collection class and its
   * shuffle method.
   */
  public void shuffle()
  {
     Collections.shuffle(Arrays.asList(cards));      
  }
    
  /**
   * Reset the deck.
   */
  public void reset()
  {
    nextCardIndex = 0;
  }
    
  /**
   * Deals a single card.
   * @throws IllegalStateException If there are no cards left in the deck.
   * @return The dealt card.
   */
  public Card deal()
  {
    if (nextCardIndex + 1 >= NUMBER_OF_CARDS)
    {
      throw new IllegalStateException("No cards left in deck");
    }
    return cards[nextCardIndex++];
  }
    
  /*
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    for(Card card : cards)
    {
      sb.append(card);
      sb.append(' ');
    }
    return sb.toString().trim();
  }   
}