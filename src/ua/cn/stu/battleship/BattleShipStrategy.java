package ua.cn.stu.battleship;

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
	private String myNavy = String.valueOf(
			"1000100001" + 
			"0000000000" + 
			"1000000000" + 
			"1000000110" +
			"0000010000" + 
			"0010010000" +
			"0010010010" +
			"0010010010" +
			"0000000000" +
			"0010000111");
	
	public Shot getNextShot(){
		int x = (int) Math.round(Math.random() * 9);
		int y = (int) Math.round(Math.random() * 9);
		return new Shot(x, y);
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
