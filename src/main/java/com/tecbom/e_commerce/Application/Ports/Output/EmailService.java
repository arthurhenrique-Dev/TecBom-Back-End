package com.tecbom.e_commerce.Application.Ports.Output;

import com.tecbom.e_commerce.Domain.ValueObjects.EmailVO;

public interface EmailService {

    void ValidateEmail(EmailVO email, String sendToken);

    void ChangePassword(EmailVO email, String sendToken);
}
