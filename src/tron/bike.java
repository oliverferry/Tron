/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */
package tron;

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class bike implements GameObj {
    	//stores the location of the bike
	protected int x;
	protected int y;
	//booleans that determine the direction the bike is moving
	protected boolean moveUp;
	protected boolean moveDown;
	protected boolean moveLeft;
	protected boolean moveRight;
   //stores everywhere the bike has been 
	protected boolean [][] board;
    private Color color;

    /**
    * Note that, because we don't need to do anything special when constructing a Square, we simply
    * use the superclass constructor called with the correct parameters.
    */
    public bike(int courtWidth, int courtHeight, Color color, int xpos, int ypos, int direction) {
        board = new boolean [courtWidth/7][courtHeight/7];
        	// for loop to set every single element in the grid to false because in the beginning of the game the bikes have not gone anywhere
        for (int i=0;i<courtWidth/7;i++){
            for (int j =0; j<courtHeight/7;j++){
                board [i][j] = false;
		}
	}
        this.color = color;
        //initializes which direction the bike is moving
		setDirection(direction);
		//stores the location of the bike
		x = xpos;
		y = ypos;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    //set the direction it moves 
    public void setDirection(int num){
       //param is int - 1: up, 2: down, 3:left,, 4:right
       if (num==1){
           	moveUp=true;
            moveDown=false;
            moveRight=false;
            moveLeft=false;
       }
       else if(num==2){
            moveUp=false;
            moveDown=true;
            moveRight=false;
            moveLeft=false;
       }
       else if(num==3){
            moveUp=false;
            moveDown=false;
            moveRight=false;
            moveLeft=true;
       }
      else if(num==4){
            moveUp=false;
            moveDown=false;
            moveRight=true;
            moveLeft=false;

       }
    }
    
        	//changes the location of the bike by 7 pixels depending on which direction it is moving
	public void move (){
		if (moveUp){
			y-=1;
            board[x][y]=true;
		}
		else if (moveDown ){
			y+=1;
            board[x][y]=true;
		}
		else if (moveLeft){
			x-=1;
            board[x][y]=true;
		}
		else if (moveRight ){
			x+=1;
            board[x][y]=true;
		}
	}

   
    public void draw(Graphics g) {
        for(int i=0;i<board[0].length;i++){
            for(int j=0;j<board.length;j++){
                if (board [j][i] == true){
                g.setColor(this.color);
                g.fillRect(j*7, i*7, 7, 7);
                }
            }
        }
    }
    

}
