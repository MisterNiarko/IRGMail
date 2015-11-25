package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;

public class MailParser {
	//private boolean textIsHtml = false;
	public ArrayList<String> filenames;
	/**
	 * Return the primary text content of the message.
	 */

	public MailParser(){
		filenames = new ArrayList<>();
	}
	public String getText(Part p) throws
	MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String)p.getContent();
			//textIsHtml = p.isMimeType("text/html");
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart)p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart)p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}

	public void getImages(Part p, String ext) throws MessagingException, IOException {

		if (p.isMimeType("image/*") && !ext.isEmpty()) {
			try(InputStream is = p.getInputStream();){          
				BufferedImage im = ImageIO.read(is);
				String fileName = "webroot/tmp/" + p.getFileName() + "." + ext;
				File imageFile = new File(fileName);
				ImageIO.write(im,ext,imageFile);
				is.close();
				filenames.add(fileName);
				return;
			}
		}

		if (p.isMimeType("multipart/related")) {
			Multipart mp = (Multipart)p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("image/jpeg")) {
					getImages(bp, "jpg");
				}
				else if (bp.isMimeType("image/gif")) {
					getImages(bp,"gif");
				}
				else if (bp.isMimeType("image/bmp")) {
					getImages(bp, "bmp");
				}
				else if (bp.isMimeType("image/png")) {
					getImages(bp,"png");
				}
			}
			return;
		}
		else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart)p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				getImages(mp.getBodyPart(i), "");
			}
		}
		return;
	}

}
