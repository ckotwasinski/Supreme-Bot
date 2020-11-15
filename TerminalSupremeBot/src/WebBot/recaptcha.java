package WebBot;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public class recaptcha implements Runnable {
	private int time;
	private long delayTime;
	public void run() {
		try {
			Thread.sleep(this.delayTime);
			String url = "http://2captcha.com/in.php";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			String urlParameters = "key=9d918fd5803da76b49138582d43262c9&method=userrecaptcha&googlekey=6LeWwRkUAAAAAOBsau7KpuC9AV-6J8mhw4AjC3Xz&pageurl=https://www.supremenewyork.com/checkout";
			
			con.setRequestMethod("POST");
			
			
			// Send post request
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			//print result
			System.out.println(response.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public recaptcha(int time) {
		this.time = time;
		this.delayTime = time - System.currentTimeMillis();
	}
}
