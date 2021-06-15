package com.appdeveloperblog.app.ws.shared;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.appdeveloperblog.app.ws.shared.dto.UserDto;

public class AmazonSES {

//	Verified in AWS
	final String EMAIL = "sagarc99@hotmail.com";

	final String SUBJECTLINE = "Verification step required to complete PhotoApp registration";

	final String HTMLBODY = "<h1>Pleae verify your email address.</h1>"
			+ "<p>Please click on the following link to complete the registration process: "
			+ "<a href='http://localhost:8080/verification-service/email-verification.html?token=$tokenValue'>"
			+ "Please Click Here</a>";

	final String TEXTBODY = "Pleae verify your email address. "
			+ "Please click on the following link to complete the registration process: "
			+ "http://localhost:8080/verification-service/email-verification.html?token=$tokenValue";

	public void verifyEmail(UserDto userDto) {			
		AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_2)
				.build();

		String htmlBody = HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
		String textBody = TEXTBODY.replace("$tokenValue", userDto.getEmailVerificationToken());

		SendEmailRequest request = new SendEmailRequest()
				.withDestination(new Destination().withToAddresses(userDto.getEmail()))
				.withMessage(new Message()
						.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBody))
								.withText(new Content().withCharset("UTF-8").withData(textBody)))
						.withSubject(new Content().withCharset("UTF-8").withData(SUBJECTLINE)))
				.withSource(EMAIL);
		
		client.sendEmail(request);

	}
}
