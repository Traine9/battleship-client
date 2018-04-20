package ua.cn.stu.battleship;

import java.util.Scanner;

public class RestClient {
	
	public static final String USER_NAME = "askosyr";

	public static void main(String[] args) {
		BattleShipWarrior thread = new BattleShipWarrior(USER_NAME, "localhost", "9999");
		thread.setStrategy(new BattleShipStrategy());
		Thread th = new Thread(thread);
		th.start();
		
		System.out.println(USER_NAME + ".Enter your opponent ID: ");
		Scanner scanner = new Scanner(System.in);
		String username = scanner.nextLine();
		
		thread.setOpponentId(username);
		scanner.close();
		
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
	}

}
