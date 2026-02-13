package com.tecbom.e_commerce.Infra.Adapters.Output.Gateways.EmailGateway;

import com.tecbom.e_commerce.Application.Ports.Output.EmailService;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ProcessingErrorException;
import com.tecbom.e_commerce.Domain.ValueObjects.EmailVO;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmailGateway implements EmailService {

    @Value("sua_api_key_aqui")
    private String apiKey;

    @Value("seu_email_aqui@gmail.com")
    private String companyEmail;

    @Override
    public void ValidateEmail(EmailVO email, String sendToken) {
        send(email.email(), "Validação de Email", "Seu token é " + sendToken);
    }

    @Override
    public void ChangePassword(EmailVO email, String sendToken) {
        send(email.email(), "Alteração de Senha", "Seu token é " + sendToken);
    }

    private void send(String email, String subject, String sendToken) {
        Email from = new Email(companyEmail);
        Email to = new Email(email);
        Content content = new Content("text/plain", "Seu token é: " + sendToken);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        } catch (IOException ex) {
            throw new ProcessingErrorException();
        }
    }
}
