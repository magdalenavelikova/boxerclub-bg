package bg.boxerclub.boxerclubbgrestserver.service.dog;

import bg.boxerclub.boxerclubbgrestserver.model.dto.dog.DogViewDto;
import bg.boxerclub.boxerclubbgrestserver.model.entity.UserEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Service
public class ChangeOwnershipDogMailService {

    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;

    public ChangeOwnershipDogMailService(
            MessageSource messageSource,
            JavaMailSender javaMailSender) {

        this.messageSource = messageSource;
        this.javaMailSender = javaMailSender;
    }


    public void sendEmail(UserEntity currentOwner, UserEntity newOwner,
                          DogViewDto dog, Locale preferredLocale, String url


    ) throws UnsupportedEncodingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Locale locale = Locale.ENGLISH;

        String confirmationUrl
                = url + "Confirm?registrationNum=" + dog.getRegistrationNum() + "&newOwner=" + newOwner.getId();
        if (preferredLocale.getLanguage().equals("bg")) {
            locale = Locale.forLanguageTag("bg-BG");
        }
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom("office@boxerclub-bg.org", getContent(locale, "registration_senderName"));
            mimeMessageHelper.setTo(currentOwner.getEmail());
            mimeMessageHelper.setSubject(getContent(locale, "changeOwner_subject"));
            mimeMessageHelper.setText(generateMessageContent(preferredLocale, dog.getRegistrationNum(), newOwner.getFullName(), confirmationUrl), true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


    private String generateMessageContent(
            Locale locale,
            String registrationNum,
            String fullName,
            String url
    ) {
        StringBuilder mailContent = new StringBuilder();
        mailContent.append("  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><p> ");
        mailContent.append(getContent(locale, "changeOwner_dog")).append(" ").append(registrationNum).append("<br/><br/>");
        mailContent.append(fullName).append(" ").append(getContent(locale, "changeOwner_owner")).append("<br/><br/>");
        mailContent.append(getContent(locale, "changeOwner_confirm")).append("<br/><br/>");


        mailContent.append("</p> <table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" <tbody> <tr><td align=\"left\">\n" +
                "<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "<tbody> <tr> <td style=\"border-radius: 3px;\" bgcolor=\"#329ba8\">" +
                "<a style=\"padding: 8px 12px; border: 1px solid #329ba8;border-radius: 3px;font-family: Helvetica, Arial, sans-serif;font-size: 14px; color: #ffffff;text-decoration: none;font-weight:bold;display: inline-block;\"href=\"");
        mailContent.append(url);
        mailContent.append("\" target=\"_blank\">").append(getContent(locale, "confirm_button"))
                .append("</a></td></tr>  </tbody></table> </td> </tr> </tbody></table><p>");
        mailContent.append(getContent(locale, "registration_best"));
        mailContent.append("</p>     </td> </tr>  </table> </td>");
        return mailContent.toString();

    }

    private String getContent(Locale locale, String code) {
        return messageSource.getMessage(
                code,
                new Object[0],
                locale);
    }


}
