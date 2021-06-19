package com.appdeveloperblog.app.ws.shared;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.appdeveloperblog.app.ws.shared.dto.UserDto;

public class AmazonSES {

//	Verified in AWS
	final String EMAIL = "sagarc99@hotmail.com";

	final String EMAIL_VERIFICATION_SUBJECT_LINE = "Complete PhotoApp registration";

	final String EMAIL_VERIFICATION_HTMLBODY = "<h1>Pleae verify your email address.</h1>"
			+ "<p>Please click on the following link to complete the registration process: "
			+ "<a href='http://localhost:8080/verification-service/email-verification.html?token=$tokenValue'>"
			+ "Please Click Here</a>";

	final String EMAIL_VERIFICATION_TEXTBODY = "Pleae verify your email address. "
			+ "Please click on the following link to complete the registration process: "
			+ "http://localhost:8080/verification-service/email-verification.html?token=$tokenValue";

	final String PASSWORD_RESET_SUBJECT_LINE = "PhotoApp Password Reset";

	final String PASSWORD_RESET_HTMLBODY = "<h1>Hello $firstName! A password reset has been requested for this user.</h1>"
			+ "<p>Please click on the following link to complete the password reset process: "
			+ "<a href='http://localhost:8080/verification-service/password-reset.html?token=$tokenValue'>"
			+ "Please Click Here</a>";

	final String PASSWORD_RESET_TEXTBODY = "Hello $firstName! A password reset has been requested for this user. "
			+ "Please click on the following link to complete the password reset process: "
			+ "http://localhost:8080/verification-service/password-reset.html?token=$tokenValue";

	public void verifyEmail(UserDto userDto) {
		AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_2)
				.build();

		String htmlBody = EMAIL_VERIFICATION_HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
		String textBody = EMAIL_VERIFICATION_TEXTBODY.replace("$tokenValue", userDto.getEmailVerificationToken());

		SendEmailRequest request = new SendEmailRequest()
				.withDestination(new Destination().withToAddresses(userDto.getEmail()))
				.withMessage(
						new Message()
								.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBody))
										.withText(new Content().withCharset("UTF-8").withData(textBody)))
								.withSubject(
										new Content().withCharset("UTF-8").withData(EMAIL_VERIFICATION_SUBJECT_LINE)))
				.withSource(EMAIL);

		client.sendEmail(request);
	}

	public boolean sendPasswordReset(String firstName, String email, String token) {
		AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.US_EAST_2)
				.build();

		String htmlBody = PASSWORD_RESET_HTMLBODY.replace("$tokenValue", token).replace("$firstName", firstName);
		String textBody = PASSWORD_RESET_TEXTBODY.replace("$tokenValue", token).replace("$firstName", firstName);

		SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(email))
				.withMessage(new Message()
						.withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBody))
								.withText(new Content().withCharset("UTF-8").withData(textBody)))
						.withSubject(new Content().withCharset("UTF-8").withData(PASSWORD_RESET_SUBJECT_LINE)))
				.withSource(EMAIL);

		SendEmailResult result = client.sendEmail(request);
		if (result != null && (result.getMessageId() != null && !result.getMessageId().isEmpty())) {
			return true;
		}
		return false;

	}
}
