package tron;
import java.awt.Graphics;

public interface GameObj {
		public int getX();
		
		public int getY();
		
		public void setDirection(int direction);
		
		public void move ();
		
		public void draw (Graphics g);
}
