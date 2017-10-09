package castledefence;
import java.awt.*;
import javax.swing.*;
public class Bullet {
    //fields 
    protected Direction d;   // the cannon from which it is created from
    protected int Xposition;    // the X coordinates of the bullet
    protected int Yposition;    // the Y coordinates of the bullet
    public static int ChangePosition = 5;   // dx or dy depending on the place it is created from , it is used in {move} method 
    // bullet photos
    private ImageIcon Btop = new ImageIcon(getClass().getResource("Btop.png"));     
    private ImageIcon Bbuttom = new ImageIcon(getClass().getResource("Bbuttom.png"));
    private ImageIcon Bleft = new ImageIcon(getClass().getResource("Bright.png"));
    private ImageIcon Bright = new ImageIcon(getClass().getResource("Bleft.png"));    
    //  IN THE Constructor 
    //  1-get Direction first  
    //  2-create a bullet with initial values of X and Y coordinates 
    public Bullet(int dir) {
        setDir(dir); 
        switch(this.d)
        {
            case TOP:
                this.Xposition = 572;
                this.Yposition = 60;
                break;
            case BUTTOM:
                this.Xposition = 572;
                this.Yposition = 740;
                break;
            case LEFT:
                this.Xposition = 61;
                this.Yposition = 363;
                break;
            case RIGHT:
                this.Xposition = 1050;
                this.Yposition = 363;
                break;    
        }
    }
    //GET direction according to randomvalue given to it from a random generator in the GameArena Class
    public void setDir(int dir) {
        switch(dir) 
        {
            case 1:
                this.d=Direction.TOP;
                break;
            case 2:
                this.d=Direction.BUTTOM;
                break;
            case 3:
                this.d=Direction.LEFT;
                break;
            case 4:
                this.d=Direction.RIGHT;
                break;
        }
    }
    //drawing method of the bullet
    public void draw (Graphics g) {
        switch(this.d){
            case TOP:
                g.drawImage(Btop.getImage(), Xposition, Yposition, 40, 40, null);
                break;
            case BUTTOM:
                g.drawImage(Bbuttom.getImage(), Xposition, Yposition, 40, 40, null);
                break;
            case RIGHT:
                g.drawImage(Bright.getImage(), Xposition, Yposition, 40, 40, null);
                break;
            case LEFT:
                g.drawImage(Bleft.getImage(), Xposition, Yposition, 40, 40, null);
                break;
        }
        
    }
    // control the motion of the bullet according to the direction it is created at by changing the original value of the X or Y depending on which direction it should move in to reach the castle
    public void move(){
     switch(d)
        {
        case TOP:
                Yposition+=ChangePosition;
                break;
        case BUTTOM:
                Yposition-=ChangePosition;
                break;
        case RIGHT:
                Xposition-=ChangePosition;
                break;
        case LEFT:
                Xposition+=ChangePosition;
                break;
        }
    }
    // collision detection with Castle
    public boolean hasCollidedWithCastle(){
        return ( (this.Xposition >= 536 ) && 
                 (this.Xposition <= 636) && 
                 (this.Yposition >= 323) && 
                 (this.Yposition <= 423)
               );
    }
    // collision detection with Shield
    // 1-Check the Shield direction so the Shield is taken as a parameter
    // 2-check the collision itself
    public boolean hasCollidedWithShield(Shield sh){
        if (sh.d == Direction.TOP) {
            return ( (this.Xposition >= 522 ) && 
                 (this.Xposition <= 648) && 
                 (this.Yposition >= 270) && 
                 (this.Yposition <= 300)
               );
        }
        if (sh.d == Direction.BUTTOM) {
            return ( (this.Xposition >= 522 ) && 
                 (this.Xposition <= 648) && 
                 (this.Yposition >= 450) && 
                 (this.Yposition <= 480)
               );
        }
        if (sh.d == Direction.RIGHT) {
            return ( (this.Xposition >= 660 ) && 
                 (this.Xposition <= 690) && 
                 (this.Yposition >= 330) && 
                 (this.Yposition <= 410)
               );
        }
        if (sh.d == Direction.LEFT) {
            return ( (this.Xposition >= 480 ) && 
                 (this.Xposition <= 510) && 
                 (this.Yposition >= 330) && 
                 (this.Yposition <= 410)
               );
        }
        return false;
    }
    // increase the magnitude of the ChangePosition in order to increase the difficulity (bullets Speed is increased by increasing the magnitude of ChangePosition)
    public void setChangePosition() {
        if(this.ChangePosition < 10)
        this.ChangePosition+=1;
    }    
}