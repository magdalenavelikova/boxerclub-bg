package bg.boxerclub.boxerclubbgrestserver.service.impl.dog;

import bg.boxerclub.boxerclubbgrestserver.service.dog.DogRegistrationMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Service
public class DogRegistrationMailServiceImpl implements DogRegistrationMailService {
    private static final String ADMIN_EMAIL = "bozhidar.velikov@boxerclub-bg.org";

    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;

    public DogRegistrationMailServiceImpl(
            MessageSource messageSource,
            JavaMailSender javaMailSender) {

        this.messageSource = messageSource;
        this.javaMailSender = javaMailSender;
    }

    @Override
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

        return "<p> " +
                getContent(locale, "newDog_info") + " " + registrationNum + "." +
                "<br/><br/>" +
                getContent(locale, "newDog_approved") +
                "</p>   " +
                getContent(locale, "registration_best");


    }

    private String getContent(Locale locale, String code) {
        return messageSource.getMessage(
                code,
                new Object[0],
                locale);
    }


}
