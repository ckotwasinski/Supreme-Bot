package WebBot;
import WebBot.Account;
import WebBot.recaptcha;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import WebBot.Bot;
public class TerminalMenu {
	public static ArrayList<Account> listOfBots = new ArrayList<Account>();
	public static ArrayList<String> listOfCSRF = new ArrayList<String>();
	public static ArrayList<String> listOfRecaptcha = new ArrayList<String>();
	public static void startMenu() throws Exception {
		System.out.println("1. Add Task");
		System.out.println("2. Add Profile");
		System.out.println("3. Add CSRF Keys");
		System.out.println("4. Add ReCaptcha Keys");
		Scanner myInput = new Scanner(System.in);
		Integer response = myInput.nextInt();
		switch(response){
			case 1:
				addTask();
				break;
			case 2: 
				addAccount();
				break;
			case 3: 
				addCSRF();
				break;
			case 4: 
				addRecaptcha();
				break;
		}
	}
	public static void addTask() throws Exception {
		System.out.println("Which profile would you like to use?: ");
		for(int i = 0; i < listOfBots.size(); i++) {
			System.out.println(i +". " + listOfBots.get(i).title);
		}
		Scanner myInput = new Scanner(System.in);
		int profile = myInput.nextInt();
		Scanner q = new Scanner(System.in);
		System.out.print("\nWhich color?: ");
		String color = q.next();
		Scanner w = new Scanner(System.in);
		System.out.print("\nWhat size?: ");
		String size = w.next();
		Scanner e = new Scanner(System.in);
		System.out.print("\nWhich type of item?: ");
		String page = e.next();
		Scanner r = new Scanner(System.in);
		System.out.print("\nWhich item?: ");
		String item = r.nextLine();
		System.out.println(item);
		Bot bot = new Bot(listOfBots.get(profile).getName(), listOfBots.get(profile).getEmail(), listOfBots.get(profile).getTel(), listOfBots.get(profile).getAddress(), listOfBots.get(profile).getApt(), listOfBots.get(profile).getZip(), listOfBots.get(profile).getCity(), listOfBots.get(profile).getState(),
			listOfBots.get(profile).getCountry(), item, listOfBots.get(profile).getNumber(), listOfBots.get(profile).getCvv(), listOfBots.get(profile).getMonth(), listOfBots.get(profile).getYear(), listOfBots.get(profile).getTime(), color, size, page);
		Thread t1 = new Thread(bot);
		t1.start();
		startMenu();
	}
	public static void addAccount() throws Exception {
		Scanner myInput = new Scanner(System.in);
		Account bot  = new Account();
		System.out.print("\n\nFirst name: ");
		bot.setFirstName(myInput.next());
		Scanner q = new Scanner(System.in);
		System.out.print("\nLast name: ");
		bot.setLastName(q.next());
		Scanner w = new Scanner(System.in);
		System.out.print("\nEmail: ");
		bot.setEmail(w.next());
		Scanner e = new Scanner(System.in);
		System.out.print("\nTelephone: ");
		bot.setTel(e.next());
		Scanner r = new Scanner(System.in);
		System.out.print("\nAddress: ");
		bot.setAddress(r.nextLine());
		Scanner t = new Scanner(System.in);
		boolean acceptable = false;
		System.out.print("Is there an apt?(y/n): ");
		String response = t.next();
		do{
			acceptable = true;
			if(response.equals("y")) {
				acceptable = true;
				System.out.print("\nApt: ");
				Scanner j = new Scanner(System.in);
				bot.setApt(j.next());
			}else if(response.equals("n")) {
				bot.setApt("");
				break;
			}else {
				acceptable = false;
			}
		}while(acceptable == false);
		Scanner y = new Scanner(System.in);
		System.out.print("\nZip: ");
		bot.setZip(y.next());
		Scanner u = new Scanner(System.in);
		System.out.print("\nCity: ");
		bot.setCity(u.next());
		Scanner i = new Scanner(System.in);
		System.out.print("\nState: ");
		bot.setState(i.next());
		Scanner o = new Scanner(System.in);
		System.out.print("\nCountry: ");
		bot.setCountry(o.next());
		Scanner p = new Scanner(System.in);
		System.out.print("\nCredit/Debit card number: ");
		bot.setNumber(p.nextLine());
		Scanner a = new Scanner(System.in);
		System.out.print("\nCard expiration month: ");
		bot.setMonth(a.next());
		Scanner s = new Scanner(System.in);
		System.out.print("\nCard expiration year: ");
		bot.setYear(s.next());
		Scanner d = new Scanner(System.in);
		System.out.print("\nCVV: ");
		bot.setCvv(d.next());
		Scanner f = new Scanner(System.in);
		System.out.print("\nHour of release: ");
		bot.setHour(f.nextInt());
		Scanner g = new Scanner(System.in);
		System.out.print("\nMinutes of release: ");
		bot.setMinute(g.nextInt());
		Scanner h = new Scanner(System.in);
		System.out.print("\nSeconds of release: ");
		bot.setSeconds(h.nextInt());
		Scanner j = new Scanner(System.in);
		System.out.print("\nTitle of profile: ");
		bot.title = j.next();
		listOfBots.add(bot);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream("Profiles.bin");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			
			objectOutputStream.writeObject(listOfBots);
			fileOutputStream.close();
			objectOutputStream.close();
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		startMenu();
	}
	public static void addCSRF() throws Exception {
		Scanner h = new Scanner(System.in);
		System.out.print("Add a CSRF token: ");
		listOfCSRF.add(h.nextLine());
		startMenu();
	}
	public static void addRecaptcha() throws Exception{
		Scanner h = new Scanner(System.in);
		System.out.print("Add a reCaptcha token manually?(y/n): ");
		String response = h.next();
		boolean isTrue = true;
		do {
			if (response.equals("y")) {
				Scanner q = new Scanner(System.in);
				System.out.print("Add a reCaptcha token: ");
				listOfRecaptcha.add(q.nextLine());
				isTrue = false;
			}else if(response.equals("n")) {
				Scanner f = new Scanner(System.in);
				System.out.print("\nHour of release: ");
				int hour = f.nextInt();
				Scanner g = new Scanner(System.in);
				System.out.print("\nMinutes of release: ");
				int minute = g.nextInt();
				Scanner i = new Scanner(System.in);
				System.out.print("\nSeconds of release: ");
				int second = i.nextInt();
				int time = hour *3600000 + minute *60000 + second *1000;
				Thread t = new Thread(new recaptcha(time));
				t.start();
			}
			
		}while(isTrue);
		startMenu();
	}
	public static void main(String[] args) throws Exception {
		try {
			InputStream file = new FileInputStream("Profiles.bin");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);
			listOfBots= (ArrayList<Account>)input.readObject();
		}catch(Exception e){
			e.printStackTrace();	
		}
		
		startMenu();
	}
}
