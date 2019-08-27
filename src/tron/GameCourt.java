package tron;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.TreeSet;
import java.util.Iterator;
/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact with one another. Take
 * time to understand how the timer interacts with the different methods and how it repaints the GUI
 * on every tick().
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

    // the state of the game logic
//     private Square square; // the Black Square, keyboard control
//     private Circle snitch; // the Golden Snitch, bounces
//     private Poison poison; // the Poison Mushroom, doesn't move
    private bike p1;
    TreeSet<bike> ai=new TreeSet<bike>();
    //private bossAI2 ai;
    public int level = 1;
    public int lives = 3;

    public boolean playing = false; // whether the game is running 
    private JLabel status; // Current status text, i.e. "Running..."

    // Game constants
    public static final int COURT_WIDTH = 1400;
    public static final int COURT_HEIGHT = 560;
  //  stores which boxes have been run over;
	private boolean [][] grid;
    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 35;
    public boolean instructions = false;

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the given INTERVAL. We
        // register an ActionListener with this timer, whose actionPerformed() method is called each
        // time the timer triggers. We define a helper method called tick() that actually does
        // everything that should be done in a single timestep.
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start(); // MAKE SURE TO START THE TIMER!

        // Enable keyboard focus on the court area.
        // When this component has the keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key is pressed, by
        // changing the square's velocity accordingly. (The tick method below actually moves the
        // square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && p1.moveRight==false) {
                    p1.setDirection(3);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && p1.moveLeft==false) {
                    p1.setDirection(4);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && p1.moveUp==false) {
                    p1.setDirection(2);
                } else if (e.getKeyCode() == KeyEvent.VK_UP && p1.moveDown==false) {
                    p1.setDirection(1);
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (playing==false)
                	reset();
                } else if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                	level++;
                	lives=3;
                	playing=false;
                    reset();
                }
            }

            public void keyReleased(KeyEvent e) {
//                 square.setVx(0);
//                 square.setVy(0);
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
//         square = new Square(COURT_WIDTH, COURT_HEIGHT, Color.BLACK);
//         poison = new Poison(COURT_WIDTH, COURT_HEIGHT);
//         snitch = new Circle(COURT_WIDTH, COURT_HEIGHT, Color.YELLOW);
    	instructions=false;
    	ai.clear();
        p1 = new bike(COURT_WIDTH, COURT_HEIGHT, Color.cyan, 40, 40, 4);
    	int avg = COURT_HEIGHT/7;
    	avg/=level+1;
        for (int i=0; i<level; i++ ){
        	ai.add(new bossAI2 (COURT_WIDTH, COURT_HEIGHT, Color.orange, 140, avg*(i+1), 3));
        }
//        	if (i%2==0){
//        		ai.add(new bossAI2 (COURT_WIDTH, COURT_HEIGHT, Color.RED, 140-4*i, 40+4*i, 3));}
//        	else if (i==1){
//        		ai.add(new bossAI2 (COURT_WIDTH, COURT_HEIGHT, Color.RED, 140-4*i, 40-4, 3));}
//        	else{
//        		ai.add(new bossAI2 (COURT_WIDTH, COURT_HEIGHT, Color.RED, 140-4*i, 40-4*(i-1), 3));}
//        }
        //ai = new bossAI2 (COURT_WIDTH, COURT_HEIGHT, Color.RED, 140, 40, 3);
        //initializes the state of the 2d array
        grid = new boolean [COURT_WIDTH/7][COURT_HEIGHT/7];
        for (int i=0;i<COURT_WIDTH/7;i++){
    		for (int j =0; j<COURT_HEIGHT/7;j++){
    			grid [i][j] = false;
    		}
    	}
    	for (int j =0; j<COURT_HEIGHT/7;j++){ 	
    		grid [0][j] = true;
    	}
    	for (int j =0; j<COURT_WIDTH/7;j++){
    		grid [j][0] = true;
    	}
    	for (int j =0; j<COURT_HEIGHT/7;j++){
    		grid [COURT_WIDTH/7-1][j] = true;
    	}
    	for (int j =0; j<COURT_WIDTH/7;j++){
    		grid [j][COURT_HEIGHT/7-1] = true;
    	}
        playing = true;
        status.setText("Level: "+level + "          lives: "+lives);

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor triggers.
     */
    void tick() {
        if (playing) {
            // advance the square and snitch in their current direction.
//             square.move();
//             snitch.move();
            p1.move();
            Iterator<bike> itr = ai.iterator(); 
            while (itr.hasNext()) {
            	bossAI2 l = (bossAI2) itr.next();
            	if (l.alive==true){
            			int i = l.nextStep(grid,p1.getX(),p1.getY());
            			l.setDirection(i);
            			l.move();
            		}
            	}


            
    		// checking the red bike death scenario
    		//----------------------------------------------------------------------------------------------------------


    		//this also checks the position of the bike, divides it by 7 and then looks into the boolean grid to see if that position has already been set to true
    		//basically this checks if the bike has ran into the trail thing of another bike (or itself)
            
            Iterator<bike> itr2 = ai.iterator(); 
            while (itr2.hasNext()) {
            		bossAI2 l = (bossAI2) itr2.next();
            		if (grid [p1.getX()][p1.getY()]==true || (p1.getX()==l.getX() && p1.getY()==l.getY())){
            			playing = false;
            			lives--;
            			if (lives==0){
            				level=1;
            				status.setText("GAME OVER");
            				lives=3;
            			}
            			else
            		        status.setText("Level: "+level + "       lives: "+lives);
            		}
            		if (grid [l.getX()][l.getY()]==true){
            			l.alive=false;
            		}
            }
            
            boolean alldead=true;
            Iterator<bike> itr3 = ai.iterator(); 
            while (itr3.hasNext()) {
            	bossAI2 l = (bossAI2) itr3.next();
            	if (l.alive==true)
            		alldead=false;
            }
            if (alldead==true){
            	level++;
            	lives=3;
            	playing = false;
            	if (level==10){
            		status.setText("Congradulations! You beaten the final level");
            	} else{
            		status.setText("Victory!  Hit the spacebar for next stage");
    			}
            }

    		//-----------------------------------------------------------------------------------------------------------------------
    		//if it has not ran into the trail of another bike, then it will set that position equal to true to mark that it has already been there
    		if (grid[p1.getX()][p1.getY()]==false){
    				grid[p1.getX()][p1.getY()]=true;
    		}
    		Iterator<bike> itr4 = ai.iterator();
    		while (itr4.hasNext()) {
    			bossAI2 l = (bossAI2) itr4.next();
    			if (grid[l.getX()][l.getY()]==false){
    				grid[l.getX()][l.getY()]=true;
    		}
    		}


//             // make the snitch bounce off walls...
//             snitch.bounce(snitch.hitWall());
//             // ...and the mushroom
//             snitch.bounce(snitch.hitObj(poison));

//             // check for the game end conditions
//             if (square.intersects(poison)) {
//                 playing = false;
//                 status.setText("You lose!");
//             } else if (square.intersects(snitch)) {
//                 playing = false;
//                 status.setText("You win!");
//             }

            // update the display
            repaint();
        }
        if (instructions==true)
        	repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//         square.draw(g);
//         poison.draw(g);
//         snitch.draw(g);
        
        g.setColor(Color.black);
        g.fillRect(0,0,COURT_WIDTH,COURT_HEIGHT);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,COURT_WIDTH,7);
        g.fillRect(0,0,7,COURT_HEIGHT);
        g.fillRect(0,COURT_HEIGHT-7,COURT_WIDTH,7);
        g.fillRect(COURT_WIDTH-7,0,7,COURT_HEIGHT);
        
        
        if (playing==false && instructions == true){
        	g.setFont(new java.awt.Font("Century Schoolbook L", 2, 24));
        	g.drawString("Instructions: ", 75, 75);
        	g.drawString("In this game you will be put in Tron's blue light cycle pitted against C2P's yellow light cycles, your main antagonist.", 75, 105);
        	g.drawString("Each Light cycle leaves a jet trail behind it, which if crashed into is fatal. Your objective is to eliminate opponents ", 75, 135);
        	g.drawString("by forcing them into into walls or jet trails while simultaneously staying alive. Every time you win, you will be elevated ", 75, 165);
        	g.drawString("to the next round where you must face additional enemy light cycles. You will control the directions of your light cycle  ", 75, 195);
        	g.drawString("using the arrow keys, and hit the spacebar to proceed through the levels or reset the game.  ", 75, 225);
        	
        	g.drawString("(Hint): The enemy AI's use the location of your light cycle and unless isolated, will move towards your location", 75, 275);
        	g.drawString("and cut you off. Make sure to leave jet trails between your bikes. Also, the AI's can sense when they are enclosed ", 75, 305);
        	g.drawString("in an isolated area and will change their strategy to conserve as much space as possible. Do not isolate yourself ", 75, 335);
        	g.drawString("unless you have significantly more space than the AI", 75, 365);
        	}
        else {       
        	p1.draw(g);
        	Iterator<bike> itr4 = ai.iterator();
        	while (itr4.hasNext()) {
        		bossAI2 l = (bossAI2) itr4.next();
        		l.draw(g);
        	}
        }
 

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}
