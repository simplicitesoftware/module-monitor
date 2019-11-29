package com.simplicite.commons.Monitor;

import java.util.ArrayList;

import com.simplicite.util.Grant;
import com.simplicite.util.Mail;
import com.simplicite.util.ObjectDB;
import com.simplicite.util.ObjectField;
import com.simplicite.util.Tool;

/**
 *
 * MailTool
 *
 * Usage :
 *
 *  MailTool mail = new MailTool(getGrant());
 *	mail.addRcpt("contact@simplicite.fr");
 *	mail.setSubject("Test Template");
 *	mail.addAttach(this, getField("fileAttribute")); //optional
 *	mail.setContent("<p>Hello World</p>");
 *	mail.send();
 *
 */
public class MailTool implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Mail mail;
	protected Grant g;
	private ArrayList<String> to;
	private ArrayList<String> cc;
	private ArrayList<String> bcc;
	private String from;
	private String replyTo;
	private String subject;
	private String content;
	private ArrayList<Mail.MailImage> img;
	private ArrayList<Mail.MailAttach> attach;

	public MailTool(){
		g = Grant.getSystemAdmin();
		mail = new Mail(g);
		to = new ArrayList<>();
		cc = new ArrayList<>();
		bcc = new ArrayList<>();
		subject = "Subject";
		from = g.getParameter("EMAIL_DEFAULT_SENDER", "");
		replyTo = null;
		img = new ArrayList<>();
		attach = new ArrayList<>();
		setContent("...");
	}

	final public void addRcpt(String mail){
		to.add(mail);
	}

	final public void addRcpt(String[] mails){
		for(String mail : mails)
			addRcpt(mail);
	}

	final public void addCc(String mail){
		cc.add(mail);
	}

	final public void addCc(String[] mails){
		for(String mail : mails)
			addCc(mail);
	}

	final public void addBcc(String mail){
		bcc.add(mail);
	}

	final public void addBcc(String[] mails){
		for(String mail : mails)
			addBcc(mail);
	}

	final public void setReplyTo(String reply) {
		if (!Tool.isEmpty(reply))
			replyTo = reply;
	}

	final public void addAttach(ObjectDB inst, ObjectField field){
		attach.add(mail.documentAttach(inst, field));
	}

	final public String addImage(Mail.MailImage image) {
		img.add(image);
		return image.getRefId();
	}

	final public void setSubject(String sjt){
		subject = sjt;
	}

	final public void setFrom(String mail) {
		if (!Tool.isEmpty(from))
			from = mail;
	}

	final public Mail getMail() {
		return mail;
	}

	public void setContent(String c) {
		content = c;
	}

	final public void send(){
		String[] rcpt = to.toArray(new String[to.size()]);
		String[] copie = cc.size()>0 ? cc.toArray(new String[cc.size()]) : null;
		String[] bcopie = bcc.size()>0 ? bcc.toArray(new String[bcc.size()]) : null;
 		Mail.MailAttach[] attachements = attach.size()>0 ? attach.toArray(new Mail.MailAttach[attach.size()]) : null;
		Mail.MailImage[] images = img.size()>0 ? img.toArray(new Mail.MailImage[img.size()]) : null;
		mail.sendWithAttach(rcpt, from, replyTo, copie, bcopie, subject, content, Mail.MAIL_MIME_TYPE_HTML, null, attachements, images);
	}
}