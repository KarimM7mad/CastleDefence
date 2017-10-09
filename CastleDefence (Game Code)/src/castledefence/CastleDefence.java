package castledefence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//used as a Direction tag (consider it as a switch)
enum Direction {    TOP, BUTTOM, RIGHT, LEFT  }
//It is mainly concerned in the game Frame as a whole not the actions in it (Appearance only)
class Castlee extends JFrame {    
    private JPanel but = new JPanel();//Buttom part
    // all are public static as they are independent of the panel object and for easy access
    public static int score = 0;
    public static JLabel SCORE = new JLabel("Score = " + score);
    public static int Level = 1;
    public static JLabel LEVEL = new JLabel("Level = " + Level);
    public static int health = 10;
    public static JLabel Health = new JLabel("Health = " + health);
    private GameArena mid; // game Arena itself
    protected javax.swing.Timer timer; 
    public Castlee() {
        this.mid = new GameArena();
        this.setTitle("Castle Defence");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(410, 60, 1154, 900);
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        this.setResizable(false);
//lower part
        but.setLayout(new FlowLayout());
        but.setPreferredSize(new Dimension(1154, 30));
        but.setBackground(Color.black);
        SCORE.setForeground(Color.yellow);
        LEVEL.setForeground(Color.yellow);
        Health.setForeground(Color.yellow);
        but.add(SCORE);
        but.add(LEVEL);
        but.add(Health);
        c.add(but, BorderLayout.SOUTH);
//Middle Part
        c.add(mid);
        //timer to refresh the whole JFrame and to make change of shield position fast
        timer = new javax.swing.Timer(5,new ActionListener(){
            public void actionPerformed(ActionEvent e) {
            mid.repaint();
            but.repaint();
            }
        });
        timer.start();
    }
}
public class CastleDefence {
    public static void main(String[] args) {
        new Castlee().setVisible(true);
    }
}
