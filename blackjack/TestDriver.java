import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.io.IOException;

public class TestDriver
{
  public static void main(String[] args) throws IOException
  {
      JOptionPane.showMessageDialog(null, "Welcome to FSU's Casino!");
      JFrame frame = new JFrame("Blackjack");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
      frame.setBackground(Color.white);
      frame.getContentPane().add(new TablePanel());
      frame.setResizable(false);
      frame.pack();
      frame.setVisible(true);
  }
}