package ua.cn.stu.battleship;

import java.util.Scanner;

public class RestClient {

	public static void main(String[] args) {
		
		String USER_NAME = "askosyr";
		
		BattleShipWarrior thread = new BattleShipWarrior(USER_NAME, "localhost", "9999");
		thread.setStrategy(new BattleShipStrategy());
		Thread th = new Thread(thread);
		th.start();
		
//		try {
//			Thread.sleep(3000l);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		
		System.out.println("Enter your opponent ID: ");
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
