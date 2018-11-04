package service;

import javax.json.bind.annotation.JsonbAnnotation;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@JsonbAnnotation
public class SMSJSONResponse {
	String message;
	String error;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	@Override
	public String toString() {
		return "SMSJSONResponse [message=" + message + ", error=" + error + "]";
	}
	
	
}
