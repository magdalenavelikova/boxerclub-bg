package bg.boxerclub.boxerclubbgrestserver.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

@Service
public class UserRegistrationMailService {


    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;

    public UserRegistrationMailService(
            MessageSource messageSource,
            JavaMailSender javaMailSender) {

        this.messageSource = messageSource;
        this.javaMailSender = javaMailSender;
    }

//    public void sendVerificationEmail(UserRegistrationDTO userRegistrationDTO, String siteUrl) throws MessagingException, UnsupportedEncodingException {
//        Optional<Users> user = this.usersRepository.findByEmail(userRegistrationDTO.getEmail());
//        String subject = "Successful Registration";
//        String senderName = "Pastry Shop Team";
//        String mailContent = "<h4>Dear " + userRegistrationDTO.getFirstName()
//                + " " + userRegistrationDTO.getLastName() + ",</h4>";
//        mailContent += "<p>Thank you for registration</p>";
//        String verifyUrl = siteUrl + "/verify/" + user.get().getVerificationCode();
//        mailContent += "<p>Please click on the \"ACTIVATE\" link to activate your account.<p/>";
//        mailContent += "<h3><a href=\"" + verifyUrl + "\">ACTIVATE</a></h3>";
//        mailContent += "<p>Mom's sweet shop team<p/>";
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//        helper.setFrom("ivailoali@gmail.com", senderName);
//        helper.setTo(userRegistrationDTO.getEmail());
//        helper.setSubject(subject);
//        helper.setText(mailContent, true);
//        javaMailSender.send(message);
//    }

    public void sendVerificationEmail(
            String fullName,
            String userEmail,
            Locale preferredLocale,
            String confirmationUrl
    ) throws MessagingException, UnsupportedEncodingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        Locale locale = Locale.ENGLISH;
        if (preferredLocale.getLanguage().equals("bg")) {
            locale = Locale.forLanguageTag("bg-BG");
        }
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setFrom("office@boxerclub-bg.org", getContent(locale, "registration_senderName"));
            mimeMessageHelper.setTo(userEmail);
            mimeMessageHelper.setSubject(getContent(locale, "registration_subject"));
            mimeMessageHelper.setText(generateMessageContent(preferredLocale, confirmationUrl), true);
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


    private String generateMessageContent(
            Locale locale, String url
    ) {
        StringBuilder mailContent = new StringBuilder();
        mailContent.append("  <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"><tr><td><p> ");
        mailContent.append(getContent(locale, "registration_tanks"));
        mailContent.append("</p> <table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\" <tbody> <tr><td align=\"left\">\n" +
                "<table  border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
                "<tbody> <tr> <td style=\"border-radius: 3px;\" bgcolor=\"#329ba8\">" +
                "<a style=\"padding: 8px 12px; border: 1px solid #329ba8;border-radius: 3px;font-family: Helvetica, Arial, sans-serif;font-size: 14px; color: #ffffff;text-decoration: none;font-weight:bold;display: inline-block;\"href=\"");
        mailContent.append(url);
        mailContent.append("\" target=\"_blank\">").append(getContent(locale, "registration_button")).append("</a></td></tr>  </tbody></table> </td> </tr> </tbody></table><p>");
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
