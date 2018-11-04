package service;

import javax.json.bind.annotation.JsonbAnnotation;
import javax.xml.bind.annotation.XmlRootElement;

import sun.security.util.Length;

@XmlRootElement
@JsonbAnnotation
public class SMSObject {
	private String fromNumber;
	private String toNumber;
	private String text;
	
	
	public String getFromNumber() {
		return fromNumber;
	}
	public void setFromNumber(String fromNumber) {
		this.fromNumber = fromNumber;
	}
	public String getToNumber() {
		return toNumber;
	}
	public void setToNumber(String toNumber) {
		this.toNumber = toNumber;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "SMSObject [fromNumber=" + fromNumber + ", toNumber=" + toNumber + ", text=" + text + "]";
	}
	
	//Method to check if a given SMS object is valid or not.
	public boolean checkValidSMS() {
		if(toNumber == null || fromNumber == null || text == null||
			toNumber.length() < 6 || toNumber.length() > 16 ||
			fromNumber.length()<6 || fromNumber.length()>16 ||
			text.length() > 120) {
			return false;
		}
		return true;
	}
	
	
}
