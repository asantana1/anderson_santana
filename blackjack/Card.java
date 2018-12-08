/**
 * A generic playing card.
 * @author Anderson Santana
 */
public class Card implements Comparable<Card>
{
  private final int rank, suit;
  private int value;
  
  // The number of ranks in a deck of cards.
  public static final int NUMBER_OF_RANKS = 13;
  
  // The number of suits in a deck of cards.
  public static final int NUMBER_OF_SUITS = 4;
  
  // The ranks.
  private final int DEUCE    = 0;
  private final int THREE    = 1;
  private final int FOUR     = 2;
  private final int FIVE     = 3;
  private final int SIX      = 4;
  private final int SEVEN    = 5;
  private final int EIGHT    = 6;
  private final int NINE     = 7;
  private final int TEN      = 8;
  private final int JACK     = 9;
  private final int QUEEN    = 10;
  private final int KING     = 11;
  private final int ACE      = 12;
  
  // The suits.
  private final int DIAMONDS = 0;
  private final int CLUBS    = 1;
  private final int HEARTS   = 2;
  private final int SPADES   = 3;
  
  // The actual rank symbols.
  private final String[] RANK_SYMBOLS = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
  // The actua suit symbols.
  private final char[] SUIT_SYMBOLS = {'d', 'c', 'h', 's'};
  
  /**
   * Constructor to create a card from a given rank and suit, each represented as integers.
   * @param rank The rank.
   * @param suit The suit.
   * @throws IllegalArgumentException If the rank or suit is invalid.
   */
  public Card(int rank, int suit)
  {
    if(rank < 0 || rank > NUMBER_OF_RANKS - 1) 
    {
      throw new IllegalArgumentException("Invalid rank.");
    }
    if(suit < 0 || suit > NUMBER_OF_SUITS - 1)
    {
      throw new IllegalArgumentException("Invalid suit.");
    }
    this.rank = rank;
    this.suit = suit;
    if(rank >= 0 && rank < 8)
      value = rank + 2;
    else if(rank > 7 && rank < 12)
      value = 10;
    else
      value = 11;
  }  
  
  /**
   * Returns the suit.
   * @return The suit.
   */
  public int getSuit() 
  {
    return suit;
  }
  
  /**
   * Returns the rank.
   * @return The rank.
   */
  public int getRank()
  {
    return rank;
  }
  
  /**
   * Returns the value.
   * @return The value of the card.
   */
  public int getValue()
  {
    return value;
  }
  
  /*
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return(rank * NUMBER_OF_SUITS + suit);
  }
  
  /*
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj)
  {
    if(obj instanceof Card)
      return((Card) obj).hashCode() == hashCode();
    else 
      return false;
  }

  /*
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Card card) 
  {
    int thisValue = hashCode();
    int otherValue = card.hashCode();
    if(thisValue < otherValue)
      return -1;
    else if(thisValue > otherValue)
      return 1;
    else
      return 0;
  }
  
  /*
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return RANK_SYMBOLS[rank] + SUIT_SYMBOLS[suit];
  }
}