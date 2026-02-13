package com.tecbom.e_commerce.Domain.Entities.Users;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.ValidationFailedException;
import com.tecbom.e_commerce.Domain.ValueObjects.*;

public class User {

    private final Cpf cpf;
    private final Name name;
    private Password password;
    private EmailVO email;
    private Address address;
    private PhoneNumber phoneNumber;
    protected Role role;
    private Status status;
    private EmailValidation emailValidation;
    private PasswordUpdater passwordUpdater;

    public User(Cpf cpf, Name name, Password password, EmailVO email, Address address, PhoneNumber phoneNumber, Role role) {
        this.cpf = cpf;
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = Status.VALIDATING;
        this.emailValidation = EmailValidation.Start();
        this.passwordUpdater = null;
    }

    public User(Cpf cpf, Name name, Password password, EmailVO email, Address address, PhoneNumber phoneNumber, Role role, Status status, EmailValidation emailValidation, PasswordUpdater passwordUpdater) {
        this.cpf = cpf;
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.status = status;
        this.emailValidation = emailValidation;
        this.passwordUpdater = passwordUpdater;
    }

    public void StartChangePassword() {
        if (this.emailValidation.validated()) this.passwordUpdater = PasswordUpdater.Start();
        else throw new ValidationFailedException("Necessário validar o email primeiro");
    }

    public void ChangePassword(String token, Password newPassword) {
        if (this.passwordUpdater != null && this.passwordUpdater.CheckToken(token)) {
            this.password = newPassword;
            this.passwordUpdater = null;
        } else throw new ValidationFailedException("Não é possível alterar a senha no momento");
    }

    public void ClearPasswordUpdater() {
        this.passwordUpdater = null;
    }

    public void ValidateEmail(String token) {
        this.emailValidation = this.emailValidation.Validate(token);
        this.status = Status.ON;
    }

    public EmailValidation getEmailValidation() {
        return emailValidation;
    }

    public void Deactivate() {
        if (this.status == Status.ON) this.status = Status.OFF;
        else throw new InvalidDataException("Usuário não está ativo");
    }

    public void Reactivate() {
        if (this.status == Status.OFF) this.status = Status.ON;
        else throw new InvalidDataException("Usuário não está desativado");
    }

    public PasswordUpdater getPasswordUpdater() {
        return passwordUpdater;
    }

    public void UpdateUser(EmailVO email, PhoneNumber phoneNumber, Address address) {
        if (email != null && email != this.email) this.email = email;
        if (phoneNumber != null && phoneNumber != this.phoneNumber) this.phoneNumber = phoneNumber;
        if (address != null && address != this.address) this.address = address;
    }

    public Role getRole() {
        return role;
    }

    public void HireUser() {
        if (this.role == Role.COMUM ) this.role = Role.ADMIN;
        else throw new InvalidDataException("Usuário já é contratado");
    }

    public Password getPassword() {
        return password;
    }

    public void DismissAdmin() {
        if (this.role == Role.ADMIN ) this.role = Role.COMUM;
        else throw new InvalidDataException("Usuário não é admin");
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Name getName() {
        return name;
    }

    public EmailVO getEmail() {
        return email;
    }

    public void setEmail(EmailVO email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }


    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Status getStatus() {
        return status;
    }

}
