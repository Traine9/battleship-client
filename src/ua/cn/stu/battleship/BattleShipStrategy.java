package ua.cn.stu.battleship;

import java.util.ArrayList;
import java.util.Stack;

/**
 * поле бою - 10 х 10
 * В розпорядженні гравця наступні доступні кораблі
 * 1 шт - 4 клітинки
 * 2 шт - 3 клитінки
 * 3 шт - 2 клітинки
 * 4 шт - 1 клітинки
 * 
 * Кораблі не можуть дотинатися один до одного і має бути принаймні 1 клітинка між кораблями.
 * 
 * opponentBattle - поле бою супротивника.
 * Заповнюється при грі:
 * Можливы значення - 
 * 0 - по клітинці не було пострілу
 * -1 - постріл по клітинці відбувався, корабель супротивника не був уражений
 * 1 - влучний постріл. корабель противника вражений не повністю
 * 2 - влучний постріл. корабель супротивника загинув
 *
 */
public class BattleShipStrategy {
	
	private boolean firstshot = true;
	public int prevX;
	public int prevY;
	Shot previuss;
	public ArrayList<Shot> previusShot = new ArrayList<Shot>();
	public Stack<Shot> nextShot = new Stack<Shot>();
	public final static int SHOT_MISS = -1;
	public final static int SHOT_HIT = 1;
	public final static int SHOT_TOUCHDOWN = 2;
	
	private int[][] opponentBattle = new int[][]{
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
		  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }
		};
	
	/**
	 * Розташування своїх кораблів.
	 * 1 - корабель
	 * 0 - синє море :)
	 */
		private void showarr(int d[][])
		{
			for(int i = 0;i<10;i++) {
				System.out.print("\n");
				for(int j = 0;j<10;j++)
				{
					System.out.print(d[i][j]+" ");
				}
				System.out.println();
			}
		}
		private int getoppbattlecell(int x,int y)
		{
			return opponentBattle[x][y];
					
		}
		private int getoppbattlecell(Shot shot)
		{
			return opponentBattle[shot.getX()][shot.getY()];
					
		}
	private String myNavy = String.valueOf(
			"0000000001" + 
			"0000000000" + 
			"1000001111" + 
			"1000000000" +
			"0000000001" + 
			"0010001000" +
			"0010001001" +
			"0010000001" +
			"0000100000" +
			"0010001110");
	public boolean check(Shot shot)
	{
		if(shot == null) return false;
		boolean flag = true;
		int x = shot.getX();
		int y = shot.getY();
		int xfor = x-1;
		int yfor = y-1;
		int count =0;//counter for checking diagonal cells 
		if(checkarrerr(shot))return false;
			//->>check all around
		for(int i = 0;i<3 && flag;i++){
			
			for (int j = 0; j <3 && flag; j++,count++) {
				
				Shot temp = new Shot(xfor+j, yfor+i);
				if(!checkarrerr(temp)) {
					if(count == 4 && getoppbattlecell(temp) != 0)flag = false; //check center cell
					
					if(getoppbattlecell(temp)==2) //check all cells for 2
					{	
						flag = false;
						}else if(count == 0 || count == 2 || count == 6 || count == 8 ) //check diagonal for 1
							{
								if (getoppbattlecell(temp) == 1) {
									flag = false;
								}
							}
				}
			}
		}
		
		return flag;
	}
	public void pushShotsToStack(Shot shot)
	{
		int x = shot.getX();
		int y = shot.getY();
		nextShot.push(new Shot(x+1,y));
		nextShot.push(new Shot(x-1,y));
		nextShot.push(new Shot(x,y+1));
		nextShot.push(new Shot(x,y-1));
	}
	public Shot getShotFromStack()
	{
		Shot shot = null;
		Shot temp = null;
		
		while(!nextShot.isEmpty() && !check(temp)){
			 temp = nextShot.pop();
			if(check(temp))
			shot = temp;
		};
		return shot;
	}
	public Shot getLuckyShot()
	{
		Shot shot;
		do {
			int x = (int) Math.round(Math.random() * 9);
			int y = (int) Math.round(Math.random() * 9);
			shot = new Shot(x, y);
		} while (!check(shot));
		return shot;
	}
	public boolean checkarrerr(Shot prevshot)
	{ 	
		
		int x = prevshot.getX();
		int y = prevshot.getY();
		boolean flag = true;
		
		if(x < 10 && x >= 0 && y < 10 && y >= 0)
		{
			flag = false;
		}
		//System.out.println("x: "+x+"y: "+y+"flag "+flag);
		return flag;
	}
	public void saveShot(Shot sht)
	{
		previusShot.add(sht);
		previuss = sht;
		
	}
	public Shot getNextShot(){
		
		 showarr(opponentBattle);
		if(firstshot)
		{
			firstshot=false;
			Shot sht = getLuckyShot();
			 saveShot(sht);
			 return sht;
		}
		Shot prev = previuss;
		
		if(getoppbattlecell(prev)!=-1)
		{
			if(getoppbattlecell(prev)==2)nextShot.clear();
			pushShotsToStack(prev);
		}
		Shot sht = getShotFromStack();
		if(sht==null)
		{
			sht = getLuckyShot();
		}
		
		 saveShot(sht);
		System.out.println(sht.toString());
		 return sht;
		
	}

	/**
	 * battle ship is 10 x 10.
	 * Ships:
	 * 4 cells - 1 ship
	 * 3 cells - 2 ships
	 * 2 cells - 3 ships
	 * 1 cell - 4 ships
	 * 
	 * 1 - ship presence
	 * 0 - see
	 * Ships have to have sea between each other.
	 * Ship can not have turns inside it.
	 */
	public String getShips() {
		return myNavy;
	}

	public int[][] getOpponentBattle() {
		return opponentBattle;
	}

	public void setOpponentBattle(int[][] opponentBattle) {
		this.opponentBattle = opponentBattle;
	}
	
}
