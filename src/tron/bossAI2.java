package tron;

import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class bossAI2 extends bike implements Comparable<bike>{ 
    	//stores the location of the bike
	static int p2x;
	static int p2y;
	static boolean isolated;
	public boolean alive;
	
	//booleans that determine the direction the bike is moving
	
	final int Up = 1;
	final int Down = 2;
	final int Left = 3;
	final int Right = 4;





	static int area=0;
    /**
    * Note that, because we don't need to do anything special when constructing a Square, we simply
    * use the superclass constructor called with the correct parameters.
    */
    public bossAI2(int courtWidth, int courtHeight, Color color, int x, int y, int direction) {
    	super(courtWidth, courtHeight, color, x, y, direction);
		isolated=true;
		alive=true;

		
    }

    public int nextStep (boolean grid [][], int m, int n){
    	isolated = true;
		p2x=m;
		p2y=n;
		flood(grid,getX()+1,y,x,y);
		flood(grid,x-1,y,x,y);
		flood(grid,x,y+1,x,y);
		flood(grid,x,y-1,x,y);
		//-------------------------UP---------------------------
		if (moveUp == true){
			//random number for random movement when not hitting wall
			int i=(int)(Math.random()*30);
			int l=(int)(Math.random()*50);
			int j;
			//----------------------GGGGGG
			 if( grid [x-1][y]==false && grid [x+1][y]==true && l==3 && isolated==false){
				System.out.println("avoided");
				return Left;
			}
			else if(grid [x+1][y]==false && grid [x-1][y]==true && l==3 && isolated==false){
				System.out.println("avoided");
				return Right;
			}
			//---------------------GGGGG
			//corner case scenario must move right
			else if (grid [x][y-1]==true && grid [x-1][y]==true){
				return Right;}
			//corner case scenario
			else if (grid [x][y-1]==true && grid [x+1][y]==true){
				return Left;}

			//hits wall ahead needs to decide which direction
			else if (grid [x][y-1]==true){
				//checks space to left
				int c = flood(grid,x-1,y,x,y);
				//checks space to right
				int v = flood(grid,x+1,y,x,y);
				System.out.println(c);
				System.out.println(v);
				if (c>v){
					System.out.println("Sucess!!");
					return Left;
				}
				else
					return Right;
			}
			else if(grid [x-1][y-1]==true && grid [x-1][y-2]==true && grid [x-1][y]==false && isolated==true){
				return Left;
			}
			else if(grid [x+1][y-1]==true && grid [x+1][y-2]==true && grid [x+1][y]==false && isolated==true){
				return Right;
			}

//			else if (grid[x-1][y]==true)
//				return Right;
//			else if (grid[x+1][y]==true)
//				return Left;
			else if (i==1 && grid [x-1][y]==false && grid [x+1][y]==false ){
				if (isolated==false){
				j=(int)(Math.random()*2+3);
				return j;}}
			else if (Math.abs(p2x-x)>Math.abs(p2y-y) && isolated==false && i<7){
				if (p2x-x>0 && grid [x+1][y]==false)
					return Right;
				else if (grid [x-1][y]==false)
					return Left;
			}
		}
		//------------------------DOWN---------------------------
		else if (moveDown==true){
			int i=(int)(Math.random()*30);
			int l=(int)(Math.random()*50);
			int j;
			//----------------------GGGGGG
			if( grid [x-1][y]==false && grid [x+1][y]==true && l==3 && isolated==false){
				System.out.println("avoided");
				return Left;
			}
			else if( grid [x+1][y]==false &&  grid [x-1][y]==true && l==3 && isolated==false){
				System.out.println("avoided");
				return Right;
			}
			//---------------------GGGGG
			else if (grid [x][y+1]==true && grid [x-1][y]==true)
				return Right;
			else if (grid [x][y+1]==true && grid [x+1][y]==true)
				return Left;

			else if (grid [x][y+1]==true){
				int c =flood(grid,x-1,y,x,y);
				int v=flood(grid,x+1,y,x,y);
				System.out.println(c);
				System.out.println(v);
				if (c>v){
					System.out.println("Sucess!!");
					return Left;
				}
				else
					return Right;
			}
			else if(grid [x-1][y+1]==true && grid [x-1][y+2]==true &&grid [x-1][y]==false && isolated==true){
				return Left;
			}
			else if(grid [x+1][y+1]==true && grid [x+1][y+2]==true && grid [x+1][y]==false && isolated==true){
				return Right;
			}

//			else if (grid[x-1][y]==true)
//				return Right;
//			else if (grid[x+1][y]==true)
//				return Left;	
			else if (i==1 && grid [x-1][y]==false && grid [x+1][y]==false ){
				if(isolated==false){
				j=(int)(Math.random()*2+3);
				return j;}}
			else if (Math.abs(p2x-x)>Math.abs(p2y-y) && isolated==false && i<7){
				if (p2x-x>0 && grid [x+1][y]==false)
					return Right;
				else if (grid [x-1][y]==false)
					return Left;
			}
		}
		//-------------------------RIGHT----------------------------
		else if (moveRight == true){
			int i=(int)(Math.random()*30);
			int l=(int)(Math.random()*50);
			int j;
			//----------------------GGGGGG
			if(grid [x][y-1]==false && grid [x][y+1]==true && l==3 && isolated==false){
				System.out.println("avoided");
				return Up;
			}
			else if(grid [x][y+1]==false && grid [x][y-1]==true && l==3 && isolated==false){
				System.out.println("avoided");
				return Down;
			}
			//---------------------GGGGG
			else if (grid [x+1][y]==true && grid [x][y-1]==true)
				return Down;
			else if (grid [x+1][y]==true && grid [x][y+1]==true)
				return Up;

			else if (grid [x+1][y]==true){
				int c=flood(grid,x,y+1,x,y);
				int v=flood(grid,x,y-1,x,y);
				System.out.println(c);
				System.out.println(v);
				if (c>v){
					System.out.println("Sucess!!");
					return Down;
				}
				else
					return Up;
			}
			else if(grid [x-1][y-1]==true && grid [x-2][y-1]==true && grid [x][y-1]==false && isolated==true){
				return Up;
			}
			else if(grid [x-1][y+1]==true && grid [x-2][y+1]==true && grid [x][y+1]==false && isolated==true){
				return Down;
			}

//			else if (grid[x][y+1]==true)
//				return Up;
//			else if (grid[x][y-1]==true)
//				return Down;
			else if (i==3 && grid [x][y+1]==false && grid [x][y-1]==false){
				if (isolated==false){
				j=(int)(Math.random()*2+1);
				return j;}}
			else if (Math.abs(p2y-y)>Math.abs(p2x-x) && isolated==false && i<7){
				if (p2y-y>0 && grid [x][y+1]==false)
					return Down;
				else if (grid [x][y-1]==false)
					return Up;
			}
		}
		//--------------------LEFT------------------
		else if (moveLeft == true){
			int i=(int)(Math.random()*20);
			int l=(int)(Math.random()*50);
			int j;
			//----------------------GGGGGG
			if( grid [x][y-1]==false  &&  grid [x][y+1]==true  && l==3 && isolated==false){
				System.out.println("avoided");
				return Up;
			}
			else if( grid [x][y+1]==false && grid [x][y-1]==true && l==3 && isolated==false){
				System.out.println("avoided");
				return Down;
			}
			//---------------------GGGGG
			else if (grid [x-1][y]==true && grid [x][y-1]==true)
				return Down;
			else if (grid [x-1][y]==true && grid [x][y+1]==true)
				return Up;

			else if (grid [x-1][y]==true){
				int c = flood(grid,x,y+1,x,y);
				int v = flood(grid,x,y-1,x,y);
				System.out.println(c);
				System.out.println(v);
				if (c>v){
					System.out.println("Sucess!!");
					return Down;
				}
				else
					return Up;
			}
			else if(grid [x+1][y-1]==true && grid [x+2][y-1]==true && grid [x][y-1]==false && isolated==true){
				return Up;
			}
			else if(grid [x+1][y+1]==true && grid [x+2][y+1]==true && grid [x][y+1]==false && isolated==true){
				return Down;
			}

//			else if (grid[x][y+1]==true)
//				return Up;
//			else if (grid[x][y-1]==true)
//				return Down;
			else if (i==3 && grid [x][y+1]==false && grid [x][y-1]==false){
				if (isolated == false){
				j=(int)(Math.random()*2+1);
				return j;}}
			else if (Math.abs(p2y-y)>Math.abs(p2x-x) && isolated==false && i<7){
				if (p2y-y>0 && grid [x][y+1]==false)
					return Down;
				else if (grid [x][y-1]==false)
					return Up;
			}
		}
		return 0;
		}
	
	private static int flood (boolean g[][],int x ,int y,int m,int n){
	    boolean[][] FillArray = new boolean [g.length][g[0].length];
		for(int i=0;i<g.length;i++){
			for(int j=0; j<g[0].length;j++){
				FillArray[i][j]=g[i][j];
				//System.out.println(FillArray[i][j]);
			}
		}
		area=0;
		FillArray[m][n]=true;
		return filler(FillArray,x,y);
		
	}
	
	private static int filler (boolean array[][],int x ,int y){
		if (area>10000)
			return 10000;
		if(x==p2x && y==p2y)
			isolated=false;
		if (array[x][y]==false){
			area++;
			array[x][y]=true;
			filler(array,x+1,y);
			filler(array,x,y+1);
			filler(array,x,y-1);
			filler(array,x-1,y);
		
		}

		return area;
	}
	
	@Override
    public int compareTo(bike other){
		if (super.y>other.y)
			return 1;
		else if (super.y<other.y)
			return 1;
		else
		return 0;
	}
	

}