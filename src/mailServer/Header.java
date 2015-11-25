package mailServer;

import java.util.Arrays;
import java.util.Date;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;

public class Header {
	private final String subject;
	private final Address[] from;
	private final Date receiveDate;
	private final String type;
	
	public Header(Message msg) throws MessagingException{
		this.subject = msg.getSubject();
		this.from = msg.getFrom();
		this.receiveDate = msg.getReceivedDate();
		this.type = msg.getContentType();
	}
	
	@Override
	public String toString() {
		
		return "Objet : "+this.subject +"\n From : "+Arrays.toString(this.from)+"\n Date : "+this.receiveDate.toString()+"\n Type : "+this.type;
	}
}
