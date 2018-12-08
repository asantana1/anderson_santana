import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class TablePanel extends JPanel
{
  private Deck deck;
  private BufferedImage table;
  private JButton deal, hit, stand, surrender;
  private ImageIcon[] cards;
  private ImageIcon holeCard;
  private JPanel buttonPanel;
  private boolean gameStarted, playerHit, playerStand, dealtAces;
  private Image[] cardImage;
  private Image tempImage;
  private int playerTotal, dealerTotal, index, playerHitCount, dealerHitCount, playerAceCount, dealerAceCount, playAgain;
  
  public TablePanel()
  {
    try
    {
      table = ImageIO.read(new File("Images/table.png"));
    }
    catch(IOException e)
    {
      System.out.println("Image could not be found.");
    }
    
    playerHitCount = 0;
    dealerHitCount = 0;
    playerTotal = 0;
    dealerTotal = 0;
    cardImage = new Image[Deck.NUMBER_OF_CARDS];
    
    //Dealer's hole card
    holeCard = new ImageIcon("Images/red_background.gif");
    tempImage = holeCard.getImage();
       
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(Box.createRigidArea(new Dimension(0, 410)));
    deck = new Deck();
    cards = new ImageIcon[Deck.NUMBER_OF_CARDS];
    
    deal = new JButton("Deal");
    hit = new JButton("Hit");
    stand = new JButton("Stand");
    surrender = new JButton("Surrender");
    
    hit.setEnabled(false);
    stand.setEnabled(false);
    surrender.setEnabled(false);
    
    ButtonListener listener = new ButtonListener();
    deal.addActionListener(listener);
    hit.addActionListener(listener);
    stand.addActionListener(listener);
    surrender.addActionListener(listener);
    
    buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.white);
    buttonPanel.add(deal);   
    buttonPanel.add(hit);    
    buttonPanel.add(stand);
    buttonPanel.add(surrender);
    
    setBackground(Color.white);
    setPreferredSize(new Dimension(700, 450));
    add(buttonPanel);
  }
  
  private class ButtonListener implements ActionListener
  {
    public void actionPerformed(ActionEvent event)
    {
      if(event.getSource() == deal)
      {
        deal.setEnabled(false);
        hit.setEnabled(true);
        stand.setEnabled(true);
        surrender.setEnabled(true);    
        
        gameStarted = true;
        deck.shuffle();
        while(index < 4)
        {
          cards[index] = new ImageIcon(getImagePath(deck.deal().toString()));
          cardImage[index] = cards[index].getImage();
          if(index % 2 == 0)
            playerTotal+= deck.getCard(index).getValue();
          if(playerTotal == 22)
          {
            playerTotal-= 10;
            playerAceCount++;
            playerAceCount++;
            System.out.println("Player ace count: " + playerAceCount);
          }
          if(index % 2 == 1)
            dealerTotal+= deck.getCard(index).getValue();
          if(dealerTotal == 22)
          {
            dealerTotal-= 10;
            dealerAceCount++;
            dealerAceCount++;
            System.out.println("Dealer ace count: " + dealerAceCount);
          }
          index++;
        }
        repaint();
        System.out.println("Dealer total after first deal: " + dealerTotal);
        System.out.println("My total after first deal: " + playerTotal);
        
        if(dealerTotal == 21)
        {
          playerStand = true;
          repaint();
          JOptionPane.showMessageDialog(null, "The dealer has Blackjack!");
            
          if(playerTotal == 21)
          {
              JOptionPane.showMessageDialog(null, "Blackjack!");
          }
          whoWins();
        }
        
        if(playerTotal == 21)
        {
              playerStand = true;
              repaint();
              JOptionPane.showMessageDialog(null, "Blackjack!");
              whoWins();
        }
      }
      else if(event.getSource() == hit)
      {
        playerHit = true;
        playerHitCount++;
        cards[index] = new ImageIcon(getImagePath(deck.deal().toString()));
        cardImage[index] = cards[index].getImage();
        playerTotal+= deck.getCard(index).getValue();
        
        if((deck.getCard(0).getRank() == 12 && playerTotal > 21 || deck.getCard(2).getRank() == 12 && playerTotal > 21) && !dealtAces)
        {
          System.out.println("Player dealt aces boolean activates!");
          playerTotal-= 10;
          dealtAces = true;
          playerAceCount++;
          System.out.println("Player ace count: " + playerAceCount);
        }
        else if((deck.getCard(index).getRank() == 12 && playerTotal > 21))
        {
          playerTotal-= 10;
          playerAceCount++;
          System.out.println("Player ace count: " + playerAceCount);
        }
        
        System.out.println("My new total after hitting: " + playerTotal);
        index++;
        repaint();
        if(playerTotal > 21)
        {
          JOptionPane.showMessageDialog(null, "Busted!");
          whoWins();
        }
      }
      else if(event.getSource() == stand)
      {
        dealtAces = false;
        playerStand = true;

        if((deck.getCard(1).getRank() == 12 && dealerTotal == 17) || deck.getCard(3).getRank() == 12 && dealerTotal == 17)
        {
          dealerHitCount++;
          cards[index] = new ImageIcon(getImagePath(deck.deal().toString()));
          cardImage[index] = cards[index].getImage();
          dealerTotal+= deck.getCard(index).getValue();
          System.out.println("SOFT 17: Dealer Total: " + dealerTotal);
          if((deck.getCard(1).getRank() == 12 && dealerTotal > 21 || deck.getCard(3).getRank() == 12 && dealerTotal > 21) && !dealtAces)
          {
            System.out.println("Dealer dealt aces boolean activates!");
            dealerTotal-= 10;
            dealtAces = true;
            dealerAceCount++;
            System.out.println("Dealer ace count: " + dealerAceCount);
          }
          else if(deck.getCard(index).getRank() == 12 && dealerTotal > 21)
          {
            dealerTotal-= 10;
            dealerAceCount++;
            System.out.println("Dealer ace count: " + dealerAceCount);
          }
          index++;
          repaint();
        }
        while(dealerTotal < 17)
        {
          dealerHitCount++;
          cards[index] = new ImageIcon(getImagePath(deck.deal().toString()));
          cardImage[index] = cards[index].getImage();
          dealerTotal+= deck.getCard(index).getValue();
          if((deck.getCard(1).getRank() == 12 && dealerTotal > 21 || deck.getCard(3).getRank() == 12 && dealerTotal > 21) && dealerHitCount == 1)
            dealerTotal-= 10;
          else if(deck.getCard(index).getRank() == 12 && dealerTotal > 21 || deck.getCard(index - 1).getRank() == 12 && dealerTotal > 21)
            dealerTotal-= 10;
          System.out.println("Inside stand action event loop: Dealer total: " + dealerTotal);
          index++;
          repaint();
        }
        whoWins();
      }
      else
      {
        choiceToPlayAgain();
      }
    }
    
    public void whoWins()
    {
      System.out.println("Player Total: " + playerTotal);
      System.out.println("Dealer Total: " + dealerTotal);
      if(dealerTotal > 21 || (playerTotal < 22 && dealerTotal < 22 && playerTotal > dealerTotal))
      {
        repaint();
        JOptionPane.showMessageDialog(null, "You win!");
      }
      else if(playerTotal > 21 || (dealerTotal < 22 && dealerTotal > playerTotal))
      {
          repaint();
          JOptionPane.showMessageDialog(null, "You lose!");
      }
      else
      {
        repaint();
        JOptionPane.showMessageDialog(null, "It is a draw! All wagers are being returned to the players!");
      }
      choiceToPlayAgain();
    }
    
    public String getImagePath(String cardName)
    {
      StringBuilder sb = new StringBuilder();
      sb.append("Images/");
      sb.append(cardName);
      sb.append(".gif");
      return sb.toString();
    }
    
    public void choiceToPlayAgain()
    {
      playAgain = JOptionPane.showConfirmDialog(null, "Would you like to play again?");
      if(playAgain == JOptionPane.YES_OPTION)
        restartGame();
      else
        System.exit(0);
    }
    
    public void restartGame()
    {
       System.out.println("restart game");
       deck.reset();
       index = 0;
       playerHitCount = 0;
       dealerHitCount = 0;
       playerTotal = 0;
       dealerTotal = 0;
       gameStarted = false;
       playerHit = false;
       playerStand = false;
       deal.setEnabled(true);
       hit.setEnabled(false);
       stand.setEnabled(false);
       surrender.setEnabled(false);
       repaint();
    }
  }
  
  public void paint(Graphics g)
  {
    super.paint(g);
    g.drawImage(table, 0, 0, null);
    if(gameStarted)
    {
      g.drawImage(cardImage[0], 250, 250, null); // Player's first card
      g.drawImage(cardImage[1], 250, 125, null); // Dealer's first card
      g.drawImage(cardImage[2], 275, 250, null); // Player's second card
      g.drawImage(tempImage, 275, 125, null); // Dealer's second card to be revealed later
    }
    if(playerHit)
    {
      g.drawImage(cardImage[4], 300, 250, null);
      if(playerHitCount > 1)
        g.drawImage(cardImage[5], 325, 250, null);
      if(playerHitCount > 2)
        g.drawImage(cardImage[6], 350, 250, null);
      if(playerHitCount > 3)
        g.drawImage(cardImage[7], 375, 250, null);
      if(playerHitCount > 4)
        g.drawImage(cardImage[8], 400, 250, null);
      if(playerHitCount > 5)
        g.drawImage(cardImage[9], 425, 250, null);
      if(playerHitCount > 6)
        g.drawImage(cardImage[10], 450, 250, null);
      if(playerHitCount > 7)
        g.drawImage(cardImage[11], 475, 250, null);
      if(playerHitCount > 8)
        g.drawImage(cardImage[12], 500, 250, null);
    }
    if(playerStand)
    {
      System.out.println("Dealer hit count: " + dealerHitCount);
      g.drawImage(cardImage[3], 275, 125, null);
      if(playerHitCount == 0)
      {
          if(dealerHitCount >= 1)
            g.drawImage(cardImage[4], 300, 125, null);
          if(dealerHitCount >= 2)
            g.drawImage(cardImage[5], 325, 125, null);
          if(dealerHitCount >= 3)
            g.drawImage(cardImage[6], 350, 125, null);
          if(dealerHitCount >= 4)
            g.drawImage(cardImage[7], 375, 125, null);
          if(dealerHitCount >= 5)
            g.drawImage(cardImage[8], 400, 125, null);
          if(dealerHitCount >= 6)
            g.drawImage(cardImage[9], 425, 125, null);
          if(dealerHitCount >= 7)
            g.drawImage(cardImage[10], 450, 125, null);
          if(dealerHitCount >= 8)
            g.drawImage(cardImage[11], 475, 125, null);
      }
      else if(playerHitCount == 1)
      {
        if(dealerHitCount >= 1)
            g.drawImage(cardImage[5], 300, 125, null);
          if(dealerHitCount >= 2)
            g.drawImage(cardImage[6], 325, 125, null);
          if(dealerHitCount >= 3)
            g.drawImage(cardImage[7], 350, 125, null);
          if(dealerHitCount >= 4)
            g.drawImage(cardImage[8], 375, 125, null);
          if(dealerHitCount >= 5)
            g.drawImage(cardImage[9], 400, 125, null);
          if(dealerHitCount >= 6)
            g.drawImage(cardImage[10], 425, 125, null);
          if(dealerHitCount >= 7)
            g.drawImage(cardImage[11], 450, 125, null);
          if(dealerHitCount >= 8)
            g.drawImage(cardImage[12], 475, 125, null);
      }
      else if(playerHitCount == 2)
      {
        if(dealerHitCount >= 1)
            g.drawImage(cardImage[6], 300, 125, null);
          if(dealerHitCount >= 2)
            g.drawImage(cardImage[7], 325, 125, null);
          if(dealerHitCount >= 3)
            g.drawImage(cardImage[8], 350, 125, null);
          if(dealerHitCount >= 4)
            g.drawImage(cardImage[9], 375, 125, null);
          if(dealerHitCount >= 5)
            g.drawImage(cardImage[10], 400, 125, null);
          if(dealerHitCount >= 6)
            g.drawImage(cardImage[11], 425, 125, null);
          if(dealerHitCount >= 7)
            g.drawImage(cardImage[12], 450, 125, null);
          if(dealerHitCount >= 8)
            g.drawImage(cardImage[13], 475, 125, null);
      }
      else if(playerHitCount == 3)
      {
        if(dealerHitCount >= 1)
            g.drawImage(cardImage[7], 300, 125, null);
          if(dealerHitCount >= 2)
            g.drawImage(cardImage[8], 325, 125, null);
          if(dealerHitCount >= 3)
            g.drawImage(cardImage[9], 350, 125, null);
          if(dealerHitCount >= 4)
            g.drawImage(cardImage[10], 375, 125, null);
          if(dealerHitCount >= 5)
            g.drawImage(cardImage[11], 400, 125, null);
          if(dealerHitCount >= 6)
            g.drawImage(cardImage[12], 425, 125, null);
          if(dealerHitCount >= 7)
            g.drawImage(cardImage[13], 450, 125, null);
          if(dealerHitCount >= 8)
            g.drawImage(cardImage[14], 475, 125, null);
      }
      else if(playerHitCount == 4)
      {
        if(dealerHitCount >= 1)
            g.drawImage(cardImage[8], 300, 125, null);
        if(dealerHitCount >= 2)
            g.drawImage(cardImage[9], 325, 125, null);
        if(dealerHitCount >= 3)
            g.drawImage(cardImage[10], 350, 125, null);
        if(dealerHitCount >= 4)
            g.drawImage(cardImage[11], 375, 125, null);
        if(dealerHitCount >= 5)
            g.drawImage(cardImage[12], 400, 125, null);
        if(dealerHitCount >= 6)
            g.drawImage(cardImage[13], 425, 125, null);
        if(dealerHitCount >= 7)
            g.drawImage(cardImage[14], 450, 125, null);
        if(dealerHitCount >= 8)
            g.drawImage(cardImage[15], 475, 125, null);
      }
      else if(playerHitCount == 5)
      {
        if(dealerHitCount >= 1)
            g.drawImage(cardImage[9], 300, 125, null);
          if(dealerHitCount >= 2)
            g.drawImage(cardImage[10], 325, 125, null);
          if(dealerHitCount >= 3)
            g.drawImage(cardImage[11], 350, 125, null);
          if(dealerHitCount >= 4)
            g.drawImage(cardImage[12], 375, 125, null);
          if(dealerHitCount >= 5)
            g.drawImage(cardImage[13], 400, 125, null);
          if(dealerHitCount >= 6)
            g.drawImage(cardImage[14], 425, 125, null);
          if(dealerHitCount >= 7)
            g.drawImage(cardImage[15], 450, 125, null);
          if(dealerHitCount >= 8)
            g.drawImage(cardImage[16], 475, 125, null);
      }
      else if(playerHitCount == 6)
      {
        if(dealerHitCount >= 1)
            g.drawImage(cardImage[10], 300, 125, null);
          if(dealerHitCount >= 2)
            g.drawImage(cardImage[11], 325, 125, null);
          if(dealerHitCount >= 3)
            g.drawImage(cardImage[12], 350, 125, null);
          if(dealerHitCount >= 4)
            g.drawImage(cardImage[13], 375, 125, null);
          if(dealerHitCount >= 5)
            g.drawImage(cardImage[14], 400, 125, null);
          if(dealerHitCount >= 6)
            g.drawImage(cardImage[15], 425, 125, null);
          if(dealerHitCount >= 7)
            g.drawImage(cardImage[16], 450, 125, null);
          if(dealerHitCount >= 8)
            g.drawImage(cardImage[17], 475, 125, null);
      }
    }
  }
}