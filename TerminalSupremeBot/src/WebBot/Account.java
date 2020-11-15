package WebBot;

import java.io.Serializable;

public class Account implements Serializable {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	private String name;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.name = firstName + "+" + this.lastName;
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.name = this.firstName + "+" + lastName;
		this.lastName = lastName;
	}
	private String firstName;
	private String lastName;
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		email = email.replace("@", "%40");
		this.email = email;
	}
	private String tel;
	private String address;
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		String[] parts = address.split("\\s+");
		for(int i= 0; i <parts.length; i++) {
			if(i>0) {
				parts[i] = parts[i-1] + "+" + parts[i];
				address = parts[i];
			}
		}
		this.address = address;
	}
	private String apt;
	private String zip;
	private String city;
	private String state;
	private String country;
	private String page;
	private String number;
	public String getApt() {
		return apt;
	}
	public void setApt(String apt) {
		this.apt = apt;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		String[] parts = number.split("\\s+");
		for(int i= 0; i <parts.length; i++) {
			if(i>0) {
				parts[i] = parts[i-1] + "+" + parts[i];
				number = parts[i];
			}
		}
		this.number = number;
	}
	private String month;
	private String year;
	private String cvv;
	private int hour;
	private int minute;
	private int seconds;
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.time = hour*3600000+ this.minute* 60000 + this.seconds* 1000;
		System.out.println(this.time);
		this.hour = hour;
	}
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		this.time = this.hour*3600000+ minute* 60000 + this.seconds* 1000;
		System.out.println(this.time);
		this.minute = minute;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.time = this.hour*3600000+ this.minute* 60000 + seconds* 1000;
		System.out.println(this.time);
		this.seconds = seconds;
	}
	private int time;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCvv() {
		return cvv;
	}
	public void setCvv(String cvv) {
		this.cvv = cvv;
	}
	public String title;
	
}
