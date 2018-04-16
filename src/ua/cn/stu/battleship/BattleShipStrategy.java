package ua.cn.stu.battleship;

public class BattleShipStrategy {

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
		StringBuilder sb = new StringBuilder();
		sb.append("1000100001");
		sb.append("0000000000");
		sb.append("1000000000");
		sb.append("1000000110");
		sb.append("0000010000");
		sb.append("0010010000");
		sb.append("0010010010");
		sb.append("0010010010");
		sb.append("0000000000");
		sb.append("0010000111");
		return sb.toString();
	}

	public int[][] getOpponentBattle() {
		return opponentBattle;
	}

	public void setOpponentBattle(int[][] opponentBattle) {
		this.opponentBattle = opponentBattle;
	}
	
}
