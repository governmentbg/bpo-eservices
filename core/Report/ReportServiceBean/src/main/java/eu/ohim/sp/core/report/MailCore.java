/*
 *  ReportService:: MailCore 07/10/13 21:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.report;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.document.DocumentService;
import eu.ohim.sp.core.domain.resources.Document;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.EJB;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.util.ByteArrayDataSource;
import java.util.List;
import java.util.Properties;

/**
 * User: jaraful
 * Date: 03/09/13
 * Time: 10:47
 */
public class MailCore {

    public static final String GENERAL = "general";

	@EJB(lookup="java:global/configurationLocal")
    private ConfigurationService configurationService;

	@EJB(lookup="java:global/documentLocal")
	private DocumentService documentService;

	private String username;
	private String password;

	private MailCore() {
	}

	public MailCore(ConfigurationService configurationService,
					DocumentService documentService) {
		this.configurationService = configurationService;
		this.documentService = documentService;
	}

	public void sendEmail(String mail, String subject, String content, List<Document> documentList) {
		// Gets the properties from the database
		Properties properties = getMailProperties();

		// Creates the session
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);

			// Attachs the files to the mail
			if (documentList != null) {
				addFiles(content, message, documentList);
			}

			// Sets the content of the email
			setEmailBasis(mail, subject, message);

			Transport.send(message);
		} catch (MessagingException e) {
			throw new SPException("There was an error while creating the mail.", e);
		}
	}

	private void setEmailBasis(String mail, String subject, Message message) throws MessagingException {
		message.setFrom(new InternetAddress(username));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mail));
		message.setSubject(subject);
	}

	private void addFiles(String content, Message message, List<Document> documentList) throws MessagingException {
		Multipart multipart = new MimeMultipart();

		// Adds all the files
		for (Document document : documentList) {
			Document persistedDocument = documentService.getDocument(document.getDocumentId());

            MimeBodyPart messageBodyPart = new MimeBodyPart();
			DataSource dataSource = new ByteArrayDataSource(persistedDocument.getData(),
					persistedDocument.getFileFormat());
			DataHandler dataHandler = new DataHandler(dataSource);
			messageBodyPart.setDataHandler(dataHandler);
			messageBodyPart.setFileName(document.getCustomProperties().get("realName"));
			multipart.addBodyPart(messageBodyPart);
		}
		// Adds the content to the message
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(content, "text/html");
		multipart.addBodyPart(messageBodyPart);
		// Adds the files to the message
		message.setContent(multipart);
	}

	private Properties getMailProperties() {
		Properties props = new Properties();

		String host = configurationService.getValue("mail.server.host", GENERAL);
		String port = configurationService.getValue("mail.server.port", GENERAL);
		String auth = configurationService.getValue("mail.server.auth", GENERAL);
		String ttls = configurationService.getValue("mail.server.ttls", GENERAL);
		username = configurationService.getValue("mail.server.user", GENERAL);
		password = configurationService.getValue("mail.server.pass", GENERAL);

		if (host == null || port == null || auth == null || ttls == null || username == null || password == null) {
			throw new SPException("There are missing configuration params for the email service.");
		}

		props.put("mail.smtp.auth", auth);
		props.put("mail.smtp.starttls.enable", ttls);
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);

		return props;
	}
}
