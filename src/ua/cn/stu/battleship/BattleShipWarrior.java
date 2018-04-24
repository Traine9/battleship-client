package ua.cn.stu.battleship;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class BattleShipWarrior implements Runnable {

	private String secret;
	private String uid;
	private String userId;
	private volatile String opponentId = null;
	private volatile boolean status = true;
	
	private BattleShipStrategy strategy;
	private String port;
	private String host;
	
	public BattleShipWarrior(String userId, String host, String port) {
		super();
		this.userId = userId;
		this.host = host;
		this.port = port;
	}

	@Override
	public void run() {
		register();
		setShips();
		int code = 301;
		Shot nextShot = strategy.getNextShot();
		while (status){
			if (opponentId != null){
				if (code != 301){
					nextShot = strategy.getNextShot();
				}
				code = shootNextPosition(opponentId, nextShot);
			}
			try {
				Thread.sleep(250l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setShips(){
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();

			HttpGet getRequest = new HttpGet(
					String.format("http://%s:%s/setships/%s/%s/%s", host, port, uid, secret, strategy.getShips()));

			HttpResponse response = httpClient.execute(getRequest);

			HttpEntity entity = response.getEntity();

			String res = EntityUtils.toString(entity);
			
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(res);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		} 
	}
	
	public int shootNextPosition(String opponentId, Shot shot){
		int code = -1;
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();

			HttpGet getRequest = new HttpGet(
					String.format("http://%s:%s/shoot/%s/%s/%s/%d/%d", host, port, uid, secret, opponentId, shot.getX(), shot.getY()));

			HttpResponse response = httpClient.execute(getRequest);

			HttpEntity entity = response.getEntity();

			code = response.getStatusLine().getStatusCode();
			// Check for HTTP response code: 200 = success
			String jsonResponse = EntityUtils.toString(entity);
			
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(jsonResponse);
			String status = (String) json.get("status");
			String result = (String) json.get("result");
			if (status.equals("success")){
				strategy.getOpponentBattle()[shot.getX()][shot.getY()] = ("miss".equals(result)) ? BattleShipStrategy.SHOT_MISS : 
					("hit".equals(result) ? BattleShipStrategy.SHOT_HIT : BattleShipStrategy.SHOT_TOUCHDOWN);
			} else 
			{
				JSONObject jsonStatus = (JSONObject)parser.parse(jsonResponse);
		        JSONObject obj = (JSONObject) jsonStatus.get("error");
		        Long codeLong = (long)obj.get("code");
		        code = codeLong.intValue();
			}
			System.out.println("status:" + json);
			if (code == 304){
				System.out.println("!!!Battle is finished!!!!");
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		} 
		return code;
	}
	
	public void register(){
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();

			HttpGet getRequest = new HttpGet(
					String.format("http://%s:%s/register/%s", host, port, userId));

			HttpResponse response = httpClient.execute(getRequest);

			HttpEntity entity = response.getEntity();

			// Check for HTTP response code: 200 = success
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			String res = EntityUtils.toString(entity);
			
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(res);
			JSONObject player = (JSONObject) json.get("player");
			this.secret = (String) player.get("secret");
			this.uid = (String) player.get("uid");

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		} 
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setOpponentId(String userId) {
		this.opponentId = userId;
	}

	public void setStrategy(BattleShipStrategy strategy) {
		this.strategy = strategy;
	}

}

	