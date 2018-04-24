package ua.cn.stu.battleship;

public class Shot {

	private int x;
	private int y;
	public Shot() {
		super();
		
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Shot [x=" + x + ", y=" + y + "]";
	}
	public Shot(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}
	
}
