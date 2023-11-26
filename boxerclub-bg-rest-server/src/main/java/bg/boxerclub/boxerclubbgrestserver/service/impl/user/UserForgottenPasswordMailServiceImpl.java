package bg.boxerclub.boxerclubbgrestserver.service.impl.user;

import bg.boxerclub.boxerclubbgrestserver.service.user.UserForgottenPasswordMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Service
public class UserForgottenPasswordMailServiceImpl implements UserForgottenPasswordMailService {


    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;

    public UserForgottenPasswordMailServiceImpl(
            MessageSource messageSource,
            JavaMailSender javaMailSender) {

        this.messageSource = messageSource;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendForgottenPasswordEmail(
            String fullName,
            String userEmail,
            Locale preferredLocale,
            String confirmationUrl
    ) throws UnsupportedEncodingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Locale locale = Locale.ENGLISH;
        if (preferredLocale.getLanguage().equals("bg")) {
            locale = Locale.forLanguageTag("bg-BG");
        }
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom("office@boxerclub-bg.org", getContent(locale, "registration_senderName"));
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject(getContent(locale, "forgottenPassword_subject"));
            mimeMessageHelper.setText(generateMessageContent(locale, confirmationUrl), true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


    private String generateMessageContent(
            Locale locale, String url
    ) {
        return "  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><p> " +
                getContent(locale, "forgottenPassword_text") +
                "</p> <table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" <tbody> <tr><td align=\"left\">\n" +
                "<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "<tbody> <tr> <td style=\"border-radius: 3px;\" bgcolor=\"#329ba8\">" +
                "<a style=\"padding: 8px 12px; border: 1px solid #329ba8;border-radius: 3px;font-family: Helvetica, Arial, sans-serif;font-size: 14px; color: #ffffff;text-decoration: none;font-weight:bold;display: inline-block;\"href=\"" +
                url +
                "\" target=\"_blank\">" + getContent(locale, "forgottenPassword_button") +
                "</a></td></tr>  </tbody></table> </td> </tr> </tbody></table><p>" +
                getContent(locale, "registration_best") +
                "</p>     </td> </tr>  </table> </td>";


    }

    private String getContent(Locale locale, String code) {
        return messageSource.getMessage(
                code,
                new Object[0],
                locale);
    }


}
