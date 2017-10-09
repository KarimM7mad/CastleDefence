package castledefence;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//SHIELD CLASS , CONCERNED WITH IT'S APPEARANCE ONLY
class Shield {
    protected Direction d;
    public Shield(Direction d) {
        setDir(d);
    }
    public void draw(Graphics g) {
        g.setColor(Color.white);
        switch (this.d) {
            case TOP:
                g.fillRect(522, 270, 126, 30);
                break;
            case BUTTOM:
                g.fillRect(522, 450, 126, 30);
                break;
            case LEFT:
                g.fillRect(480, 330, 30, 80);
                break;
            case RIGHT:
                g.fillRect(660, 330, 30, 80);
                break;
        }
    }
    public void setDir(Direction d) {
        this.d = d;
    }
}
// this panel is Concerned mainly with all of the actions in the Game
class GameArena extends JPanel {
    private Shield sh;
    private Bullet b;
    protected Graphics g = this.getGraphics();
    private ImageIcon castle = new ImageIcon(getClass().getResource("D.png"));          // Castle object itself
    private ImageIcon BackGround = new ImageIcon(getClass().getResource("A.jpg"));      // Background Object itself
    private ImageIcon Cannon1 = new ImageIcon(getClass().getResource("left.png"));      // Left Cannon Object itself
    private ImageIcon Cannon2 = new ImageIcon(getClass().getResource("top.png"));       // Top Cannon Object itself
    private ImageIcon Cannon3 = new ImageIcon(getClass().getResource("right.png"));     // Right Cannon Object itself
    private ImageIcon Cannon4 = new ImageIcon(getClass().getResource("down.png"));      // Buttom Cannon Object itself
    private Random rnd = new Random();  // random value generator (for the bullet creation (details are explained in the Bullet Class)
    private javax.swing.Timer WholeActionTimer; 
    private javax.swing.Timer CollisionCheckTimer ;
    private javax.swing.Timer SurvivalTimer ;
    private int SurvivalPeriod=0;
    public GameArena() {
        this.sh = new Shield(Direction.RIGHT);
        CreateBullet(4);
        // shield motion
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        sh.setDir(Direction.TOP);
                        break;
                    case KeyEvent.VK_DOWN:
                        sh.setDir(Direction.BUTTOM);
                        break;
                    case KeyEvent.VK_RIGHT:
                        sh.setDir(Direction.RIGHT);
                        break;
                    case KeyEvent.VK_LEFT:
                        sh.setDir(Direction.LEFT);
                        break;
                }
            }
        });
        //Motion timer of the Bullet
        this.WholeActionTimer = new javax.swing.Timer(10, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                b.move();
            }
        });
        this.WholeActionTimer.start();
        //Reaction taken on Collision delay timers
        CollisionCheckTimer = new javax.swing.Timer(5, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               ReactionOnCollisionWithCastle();
               ReactionOnCollisionWithShield();
            }
        });
        CollisionCheckTimer.start();
        //Calculate the time Survivied
        SurvivalTimer = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               SurvivalPeriod++;
            }
        });
        SurvivalTimer.start();
    }
    //GameArena Appearance
    public void paint(Graphics g) {
            g.drawImage(BackGround.getImage(), 0, 0, 1152, 900, null);  //Background photo
            g.drawImage(castle.getImage(), 536, 323, 100, 100, null);   //Castle itself
            g.drawImage(Cannon3.getImage(), 1080, 345, 60, 45, null);   //right cannon
            g.drawImage(Cannon1.getImage(), 0, 345, 60, 45, null);      //left cannon
            g.drawImage(Cannon2.getImage(), 550, 0, 70, 60, null);      //top cannon
            g.drawImage(Cannon4.getImage(), 550, 770, 70, 60, null);    //buttom cannon
            //left road
            g.fillRect(0, 399, 370, 30);
            g.fillRect(0, 310, 370, 30);
            //right road
            g.fillRect(780, 399, 370, 30);
            g.fillRect(780, 310, 370, 30);
            //top road
            g.fillRect(640, 0, 30, 250);
            g.fillRect(500, 0, 30, 250);
            //buttom road
            g.fillRect(640, 502, 30, 330);
            g.fillRect(500, 502, 30, 330);
            // corner 1 top left
            g.fillRect(370, 220, 130, 30);
            g.fillRect(370, 220, 30, 120);
            // corner 2 top right
            g.fillRect(670, 220, 100, 30);
            g.fillRect(770, 220, 30, 120);
            // corner 3 buttom right 
            g.fillRect(670, 502, 110, 30);
            g.fillRect(777, 399, 30, 133);
            // corner 4 buttom left
            g.fillRect(366, 399, 30, 133);
            g.fillRect(366, 502, 140, 30);
            //Shield drawing
            sh.draw(g);
            //bullet drawing
            b.draw(g);
    }
    //create a new object bullet
    public void CreateBullet(int x) {
        this.b = new Bullet(x);
    }
    //when the Bullet collide with the Castle
    /* 
    1-stop the timer 
    2-Decrease the Health
    3-if the Game is not over
    {   - create a new bullet object   - start the timer    }
    */
    public void ReactionOnCollisionWithCastle() {
        if (b.hasCollidedWithCastle()) {
            WholeActionTimer.stop();
            Castlee.health--;
            Castlee.Health.setText("Health = " + Castlee.health);
            if (!GameOver()) {
                CreateBullet(rnd.nextInt(4) + 1);
                WholeActionTimer.start();
            }
        }
    }
    //when the Bullet collide with the shield
    /* 
    1-stop the timer 
    2-increase the score
    3-create a new bullet object
    4-increase the level each 4 bullets evaded
    5-increase the Health every 3 levels by 5 points
    6-Sart the timer
    */
    public void ReactionOnCollisionWithShield() {
        if (b.hasCollidedWithShield(sh)) {
            WholeActionTimer.stop();
            Castlee.SCORE.setText("Score = " + (++Castlee.score));
            CreateBullet(rnd.nextInt(4) + 1);
            if(Castlee.score%4==0)
            {
                if(Castlee.Level<30)
                {
                    Castlee.LEVEL.setText("Level = " + (++Castlee.Level));
                    b.setChangePosition();
                    if(Castlee.Level%3 == 0 )
                        Castlee.health+=5;
                }
            }
            WholeActionTimer.start();
        }
    }
    //if the Health is zero , the Game is Stopped by stoping all timers and player is sent a message then the game quit
    public boolean GameOver() {
        if (Castlee.health <= 0) {
            WholeActionTimer.stop();
            CollisionCheckTimer.stop();
            SurvivalTimer.stop();
            JOptionPane.showMessageDialog(this, "You Survived: "+this.SurvivalPeriod+" seconds","Game Over,Loser",0);
            System.exit(0);
            return true;
        }
        return false;
    }
}