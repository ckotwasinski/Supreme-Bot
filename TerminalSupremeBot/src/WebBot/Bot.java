package WebBot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import WebBot.TwoCaptchaService;

import javax.net.ssl.HttpsURLConnection;

public class Bot implements Runnable{
	public String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36";
	private String name;
	private String email;
	private String tel;
	private String address;
	private String apt;
	private String zip;
	private String city;
	private String state;
	private String country;
	private String item;
	private String page;
	private String number;
	private String month;
	private String year;
	private String cvv;
	private int time;
	private String recaptcha;
	private String size;
	private String authToken;
	private String color;
	private String shopText;
	private String Supremesess;
	private int counter =0;
	
	private volatile boolean running = true;
	private ArrayList<String> cookies = new ArrayList<String>();
	public Bot(String name, String email, String tel, String address, String apt, String zip, String city, String state, String country,
            String item, String number, String cvv, String month, String year, int time, String color, String size, String page){
		this.name = name;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.apt = apt;
        this.zip = zip;
        this.city = city;
        this.state = state;
        this.country = country;
        this.item = item;
        this.page = page;
        this.number = number;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
        this.time = time;
        this.color = color;
        this.size = size;
        this.page = page;
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Telephone: " + tel);
        System.out.println("Address: " + address);
        System.out.println("Apt: " + apt);
        System.out.println("Zip: " + zip);
        System.out.println("City: " + city);
        System.out.println("State: " + state);
        System.out.println("Country: " + country);
        System.out.println("Item: " + item);
        System.out.println("Page: " + page);
        System.out.println("Number: " + number);
        System.out.println("Month: " + month);
        System.out.println("Year: " + year);
        System.out.println("CVV: " + cvv);
        System.out.println("Time: " + time);
        System.out.println("Color: " + color);
        System.out.println("Size: " + size);
	}
	public void run() {
		TwoCaptchaService service = null;
		try {
			//calc delay and sleep thread
			//Thread.sleep(10);
			//check shop page
   
			try{
				

			
			if(calcDelay() - 50000>0) {
				Thread.sleep(calcDelay() - 50000);
			}
				
			
			String apiKey = "";
			String googleKey = "6LeWwRkUAAAAAOBsau7KpuC9AV-6J8mhw4AjC3Xz";
			String pageUrl = "https://www.supremenewyork.com/checkout";
			
			
			 
			service = new TwoCaptchaService(apiKey, googleKey, pageUrl);
			
			}catch(Exception e){
				run();
			}
			
			try {
				String responseToken = service.solveCaptcha();
				System.out.println("The response token is: " + responseToken);
				this.recaptcha = responseToken;
			} catch (InterruptedException e) {
				System.out.println("ERROR case 1");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("ERROR case 2");
				e.printStackTrace();
			}
			
			
			if(calcDelay() - 300>0) {
				Thread.sleep(calcDelay() - 300);
			}
			
			
			System.out.println("trying page");
			boolean pageUp = false;
			do {
				pageUp = tryCheckPage();
				System.out.println("Page found: " + pageUp);
			}while(!pageUp);
			
			startPurchase();
			//open product page
			
		}catch (InterruptedException e) {
			e.printStackTrace();
		}catch(Exception e) {
			//System.out.println(e);
		}
		
		
	}
	public int calcDelay() {
		try {
			URL obj = new URL("https://www.supremenewyork.com");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			
			con.setRequestProperty("User-Agent", USER_AGENT);
	
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			Map<String, List<String>> headerFields = con.getHeaderFields();
			Set<String> headerFieldsSet = headerFields.keySet();
			Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();
			while (hearerFieldsIter.hasNext()) {
				String headerFieldKey = hearerFieldsIter.next();
				if ("Date".equalsIgnoreCase(headerFieldKey)) {
					List<String> headerFieldValue = headerFields.get(headerFieldKey);
					for (String headerValue : headerFieldValue) {
						String date = matchRegex(headerValue, "\\d\\d:\\d\\d:\\d\\d");
						String[] parts = date.split(":");
						for (String s: parts) {
							System.out.println(s);
						}
						int currentTime = Integer.parseInt(parts[0]) *3600000 + Integer.parseInt(parts[1]) * 60000 + Integer.parseInt(parts[2]) * 1000;
						System.out.println(this.time);
						System.out.println(currentTime);
						System.out.println(this.time - currentTime);
						return this.time - currentTime;
					}
				}
			}
		}catch(Exception e) {
			System.out.println(e);
			return 0;
		}
		return 0;
	}
	public String findPage(String url) throws Exception {
		try {
			URL obj = new URL("https://www.supremenewyork.com" + url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("GET");
			
			con.setRequestProperty("User-Agent", this.USER_AGENT);
			
			if(cookies.size() >0) {
				String cookie = "";
				for(int i =0; i<cookies.size(); i+=2) {
					cookie += cookies.get(i) + "=" + cookies.get(i+1) + "; ";
				}
				con.setRequestProperty("Cookie", cookie);
			}
	
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			System.out.println("THESE ARE THE GET REQUEST HEADERS: " + con.getHeaderFields());
			
			Map<String, List<String>> headerFields = con.getHeaderFields();
			Set<String> headerFieldsSet = headerFields.keySet();
			Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();
			while (hearerFieldsIter.hasNext()) {
				String headerFieldKey = hearerFieldsIter.next();
				if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
					List<String> headerFieldValue = headerFields.get(headerFieldKey);
					for (String headerValue : headerFieldValue) {
						String[] parts = headerValue.split("=");
						if (cookies.contains(parts[0])) {
							int position = cookies.indexOf(parts[0]) + 1;
							cookies.remove(position);
							cookies.add(position, parts[1]);
						}else {
							cookies.add(parts[0]);
							cookies.add(parts[1]);
						}
					}
				}
			}
			
			int responseCode = con.getResponseCode();
			File file = new File("findPage.txt");
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);
			br.write(++counter + ":\nSending 'GET' request to URL : " + obj + "\nResponse Code : " + responseCode + "\n\n" + response.toString() + "\n\n\n\n\n\n");

			br.close();
			fr.close();
			return(response.toString());
		}catch(Exception e) {
			return "";
		}
	}
	public boolean tryCheckPage() throws Exception
    {
		//this.item = "Supreme®\\/Hanes® Tagless Tees \\(3 Pack\\)";
		String pattern = "<a class=\"name-link\" href=\"\\/[a-zA-Z0-9-]+\\/[a-zA-Z0-9-]+\\/[a-zA-Z0-9-]+\\/[a-zA-Z0-9-]+\">\\s*" + this.item + "\\s*<\\/a><\\/div><div class=\"product-style\"><a class=\"name-link\" href=\"\\/[a-zA-Z0-9-]+\\/[a-zA-Z0-9-]+\\/[a-zA-Z0-9-]+\\/[a-zA-Z0-9-]+\">" + this.color + "";
    	Pattern r = Pattern.compile(pattern);
    	String page =findPage("/shop/all/" + this.page);
    	System.out.println(page);
    	Matcher m = r.matcher(page);
    	boolean result  = m.find();
    	System.out.println(result);
        if (result)
        {
        	shopText = m.group();
        	return (result);
        }else {
        	return false;
        }
        
    }


	public String matchRegex(String data, String pattern) {
	    Pattern r = Pattern.compile(pattern);
	    Matcher m = r.matcher(data);
	    if (m.find( )) {
	    	return m.group();
	    }else {
	    	return "";
	    }
		
	}
	public void startPurchase() throws Exception {
		String postNum = "";
		String productColorNum = "";
		String productSizeNum = "";
		try {
			String urlExtension = matchRegex(shopText, "\\/[a-zA-Z0-9-]+\\/[a-zA-Z0-9-]+\\/[a-zA-Z0-9-]+\\/[a-zA-Z0-9-]+");
			String sizePattern = "<option value=\"\\d+\">" + this.size + "<\\/option>";
	        String productPage = findPage(urlExtension);
	        System.out.println(productPage);
	        PrintWriter writer = new PrintWriter("productPage.txt", "UTF-8");
	        writer.println(productPage + "\n\n\n");
	        writer.close();
	        Pattern r = Pattern.compile(sizePattern);
		    Matcher m = r.matcher(productPage);
		    String productSize;
		    Boolean foundPage = m.find();
		    System.out.println(foundPage);
		    System.out.println("starting purchase");
		    if (foundPage) {
		    	System.out.println("with size");
		    	productSize = m.group();
		    }else {
		    	String pattern = "<input type=\"hidden\" name=\"s\" id=\"s\" value=\"\\d+\" \\/>";
		    	Pattern q = Pattern.compile(pattern);
			    Matcher w = q.matcher(productPage);
		    	if(w.find()) {
		    		productSize = matchRegex(productPage, pattern);
		        	System.out.println(productSize);
		        	System.out.println("without size");
		    	}
		        else {
		        	System.out.println("no size found");
		        	startPurchase();
		        	return;
		        	}
		    }
	        String productColor = matchRegex(productPage, "\"" + this.color + "\" data-style-id=\"\\d+\"");
	        String numPattern = "\\d+";
	        productSizeNum = matchRegex(productSize, numPattern);
	        productColorNum = matchRegex(productColor, numPattern);
	        String postPattern = "action=\"\\/shop\\/\\d+\\/add\"";
	        String post = matchRegex(productPage, postPattern);
	        postNum = matchRegex(post, numPattern);
	        System.out.println(productSizeNum);
	        System.out.println(productColorNum);
	        System.out.println(postNum);
	        checkout(postNum, productColorNum, productSizeNum);
		}catch(Exception e){
			startPurchase();
		}
		
	}
	
	private void checkout(String postNum, String idNum, String sizeNum) throws Exception {
		try {
			Random rand = new Random();
			//this.recaptcha = TerminalMenu.listOfRecaptcha.get(0);
			//TerminalMenu.listOfRecaptcha.remove(0);
			this.authToken = TerminalMenu.listOfCSRF.get(rand.nextInt(TerminalMenu.listOfCSRF.size()));	
			URL obje = new URL("https://www.supremenewyork.com/shop/" + postNum + "/add");
			HttpsURLConnection conc = (HttpsURLConnection) obje.openConnection();
			
			
			System.out.println("\n\n\n\n\n\n\n\nAdding To Cart");
			String urlParameter = "utf8=%E2%9C%93&st=" + idNum + "&s=" + sizeNum + "&commit=add+to+cart";
			System.out.println("hello");
			
			conc.setRequestMethod("POST");
			
			
			conc.setRequestProperty("User-Agent", this.USER_AGENT);
			conc.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			conc.setRequestProperty("Accept", "*/*;q=0.5, text/javascript, application/javascript, application/ecmascript, application/x-ecmascript");
			
			System.out.println(cookies);
			if(cookies.size() >0) {
				String cookie = "";
				for(int i =0; i<cookies.size(); i+=2) {
					cookie += cookies.get(i) + "=" + cookies.get(i+1) + "; ";
				}
				conc.setRequestProperty("Cookie", cookie);
			}
			
			// Send post request
			conc.setDoOutput(true);
			DataOutputStream wri = new DataOutputStream(conc.getOutputStream());
			wri.writeBytes(urlParameter);
			wri.flush();
			wri.close();
			
			System.out.println("THESE ARE THE COOKIES:  " +  conc.getHeaderFields());

			
			
			Map<String, List<String>> headerFields = conc.getHeaderFields();
			Set<String> headerFieldsSet = headerFields.keySet();
			Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();
			while (hearerFieldsIter.hasNext()) {
				String headerFieldKey = hearerFieldsIter.next();
				if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
					List<String> headerFieldValue = headerFields.get(headerFieldKey);
					for (String headerValue : headerFieldValue) {
						String[] parts = headerValue.split("=");
						if (cookies.contains(parts[0])) {
							int position = cookies.indexOf(parts[0]) + 1;
							cookies.remove(position);
							cookies.add(position, parts[1]);
						}else {
							cookies.add(parts[0]);
							cookies.add(parts[1]);
						}
					}
				}
			}
			
			int responseCodes = conc.getResponseCode();
			BufferedReader inc = new BufferedReader(
			        new InputStreamReader(conc.getInputStream()));
			String inputLines;
			StringBuffer responses = new StringBuffer();
	
			while ((inputLines = inc.readLine()) != null) {
				responses.append(inputLines);
			}
			inc.close();
			File fileq = new File("findPage.txt");
			FileWriter frq = new FileWriter(fileq, true);
			BufferedWriter brq = new BufferedWriter(frq);
			brq.write(++counter + ":\nSending 'POST' request to URL : " + obje + "\nResponse Code : " + responseCodes + "\n\n" + responses.toString() + "\n\n\n\n\n\n");

			brq.close();
			frq.close();   
			//print result
			Thread.sleep(2500);
			System.out.println("Checking Out");
			String url = "https://www.supremenewyork.com/checkout.json";
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			String jsonauthToken = URLEncoder.encode(authToken, "UTF-8");
			String urlParameters = "utf8=%E2%9C%93&authenticity_token=" + jsonauthToken + "&order%5Bbilling_name%5D=&cerear=RMCEAR&order%5Bemail%5D=&order%5Btel%5D=&order%5Bbilling_address%5D=&order%5Bbilling_address_2%5D=&order%5Bbilling_zip%5D=-4304&order%5Bbilling_city%5D=&order%5Bbilling_state%5D=&order%5Bbilling_country%5D=U&same_as_billing_address=1&store_credit_id=&riearmxa=&credit_card%5Bmonth%5D=10&credit_card%5Byear%5D=&credit_card%5Bmeknk%5D=&credit_card%5Bvval%5D=&order%5Bterms%5D=0&order%5Bterms%5D=1&g-recaptcha-response=" + this.recaptcha; 
			System.out.println("PARAMETERS: " + urlParameters);
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", this.USER_AGENT);
			con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			con.setRequestProperty("Accept", "*/*");
			
			System.out.println(cookies);
			if(cookies.size() >0) {
				String cookie = "";
				for(int i =0; i<cookies.size(); i+=2) {
					cookie += cookies.get(i) + "=" + cookies.get(i+1) + "; ";
				}
				con.setRequestProperty("Cookie", cookie);
				System.out.println("THESE ARE THE SENT COOKIES: " + cookie);
			}
			
			
			
			
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
			
			/*
			Map<String, List<String>> headerFields = con.getHeaderFields();
			Set<String> headerFieldsSet = headerFields.keySet();
			Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();
			while (hearerFieldsIter.hasNext()) {
				String headerFieldKey = hearerFieldsIter.next();
				if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
					List<String> headerFieldValue = headerFields.get(headerFieldKey);
					for (String headerValue : headerFieldValue) {
						String[] parts = headerValue.split("=");
						if (cookies.contains(parts[0])) {
							int position = cookies.indexOf(parts[0]) + 1;
							cookies.remove(position);
							cookies.add(position, parts[1]);
						}else {
							cookies.add(parts[0]);
							cookies.add(parts[1]);
						}
					}
				}
			}*/
			
			//print result
			File file = new File("findPage.txt");
			FileWriter fr = new FileWriter(file, true);
			BufferedWriter br = new BufferedWriter(fr);
			br.write(++counter + ":\nSending 'POST' request to URL : " + obj + "\nResponse Code : " + responseCode + "\n\n" + response.toString() + "\n\n\n\n\n\n");

			br.close();
			fr.close();   
			String redirectPattern = "slug\":\"\\w+";
	        String redirect = matchRegex(response.toString(), redirectPattern);
	        if(redirect.equals("")) {
	        	checkout(postNum, idNum, sizeNum);
	        }
	        String colonpattern = "\":\"\\w+";
	        String redirectCode = matchRegex(redirect, colonpattern);
	        if(redirectCode.equals("")) {
	        	checkout(postNum, idNum, sizeNum);
	        }
	        String codepattern = "\\w+";
	        String extension = matchRegex(redirectCode, codepattern);
	        if(extension.equals("")) {
	        	checkout(postNum, idNum, sizeNum);
	        }
	        System.out.println(extension);
	        Thread.sleep(10000);
	        System.out.println(extension);

	        
	        String result = findPage("/checkout/" + extension + "/status.json");
	        File files = new File("findPage.txt");
			FileWriter fre = new FileWriter(files, true);
			BufferedWriter bre = new BufferedWriter(fre);
			bre.write("\n\n\n\n\n\n" + result + "\n\n\n\n\n\n");
			bre.close();
			fre.close(); 

	        System.out.println(result);
	        
	        running = false;
	        return;
		}catch(Exception e) {
			checkout(postNum, idNum, sizeNum);
		}
	}

	
}
