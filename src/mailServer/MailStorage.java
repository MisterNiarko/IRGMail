package mailServer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

import io.vertx.core.AbstractVerticle;
import utilities.Config;
import utilities.MailParser;
import utilities.Property;

public class MailStorage extends AbstractVerticle{
	Map<Header,String> headers = new HashMap<>(); 
	Properties props;
	Session session;
	Store store;
	Folder inbox;

	MailStorage(Config config) throws MessagingException{
		this.init(config);
	}

	private void init(Config config) throws MessagingException{
		// Get a session instance to read mail
		session = Session.getInstance(config.getSessionProperties(), null);
		//Access emails through store class
		store = session.getStore();
		store.connect(config.getProperty(Property.SERVER), config.getProperty(Property.ADDRESS), config.getProperty(Property.PASSWORD));
		inbox = store.getFolder("INBOX");
	}

	void fetchHeaders() throws MessagingException, IOException{
		if(inbox != null){
			inbox.open(Folder.READ_ONLY);
		}
		int msgCount = inbox.getMessageCount();
		System.out.println(msgCount);
		Message[] msgs = inbox.getMessages(msgCount-3, msgCount);
		//FetchProfile fp = new FetchProfile();
		//fp.add(FetchProfile.Item.ENVELOPE);
		//inbox.fetch(msgs, fp);
		for (int i = msgs.length-1; i >= 0; i--) {
			if(i == msgs.length-1){
				System.out.println(new Header(msgs[i]).toString());
				MailParser mp = new MailParser();
				//msgs[i].getDataHandler().getDataSource().getInputStream().
				String s = mp.getText(msgs[i]);
				mp.getImages(msgs[i], "");
				System.out.println(s);
				/* -------- TEST de remplacement des cid */
				/*Pattern p = Pattern.compile("src=\"cid:.*?\"");  
				Matcher m = p.matcher(s) ;  

				StringBuffer sb =  new StringBuffer() ;  
				int j=0;
				while (m.find()) {
					System.out.println("----------trouve!");
					m.appendReplacement(sb,"src=\""+mp.filenames.get(j)+"\"");
					j++;
				}  
				m.appendTail(sb);
				System.out.println(sb);*/
				/* -------- */

			}

			/*if(i == msgs.length-1){
				System.out.println(new Header(msgs[i]).toString());
				Multipart mp = (Multipart) msgs[i].getContent();
				for(int j = 0;j<mp.getCount();j++){
					System.out.println(String.valueOf(mp.getBodyPart(j).getContent()));
					System.out.println("-");
				}
				//System.out.println(msgs[i]);
			}*/
			//System.out.println(new Header(msgs[i]).toString());
			//System.out.println("----------------------------------------------------------------");
		}
	}
}

