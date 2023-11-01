package bg.boxerclub.boxerclubbgrestserver.service.dog;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Service
public class DogRegistrationMailService {
    private static final String ADMIN_EMAIL = "bozhidar.velikov@boxerclub-bg.org";

    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;

    public DogRegistrationMailService(
            MessageSource messageSource,
            JavaMailSender javaMailSender) {

        this.messageSource = messageSource;
        this.javaMailSender = javaMailSender;
    }


    public void sendNewDogRegistrationEmail(
            String registrationNum, Locale preferredLocale


    ) throws UnsupportedEncodingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Locale locale = Locale.ENGLISH;
        if (preferredLocale.getLanguage().equals("bg")) {
            locale = Locale.forLanguageTag("bg-BG");
        }
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom("office@boxerclub-bg.org", getContent(locale, "registration_senderName"));
            mimeMessageHelper.setTo(ADMIN_EMAIL);
            mimeMessageHelper.setSubject(getContent(locale, "newDog_subject"));
            mimeMessageHelper.setText(generateMessageContent(preferredLocale, registrationNum), true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


    private String generateMessageContent(
            Locale locale,
            String registrationNum
    ) {
        StringBuilder mailContent = new StringBuilder();
        mailContent.append("<p> ");
        mailContent.append(getContent(locale, "newDog_info")).append(" ").append(registrationNum).append(".");
        mailContent.append("<br/>");
        mailContent.append("<br/>");
        mailContent.append(getContent(locale, "newDog_approved"));
        mailContent.append("</p>   ");
        mailContent.append(getContent(locale, "registration_best"));
        return mailContent.toString();

    }

    private String getContent(Locale locale, String code) {
        return messageSource.getMessage(
                code,
                new Object[0],
                locale);
    }


}
