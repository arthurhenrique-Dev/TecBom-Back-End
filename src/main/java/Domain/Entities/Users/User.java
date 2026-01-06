package Domain.Entities.Users;

import Domain.Exceptions.Exceptions.ValidationFailedException;
import Domain.ValueObjects.*;

import java.util.ArrayList;

public class User {

    private final Cpf cpf;
    private final Name name;
    private Password plaintext;
    private Email email;
    private Address address;
    private PhoneNumber phoneNumber;
    private Cart cart;
    protected Role role;
    private Status status;
    private EmailValidation emailValidation;
    private PasswordUpdater passwordUpdater;

    public Role getRole() {
        return role;
    }

    public User(Cpf cpf, Name name, Password plaintext, Email email, Address address, PhoneNumber phoneNumber, Role role, Status status) {
        this.cpf = cpf;
        this.name = name;
        this.plaintext = plaintext;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.cart = new Cart(new ArrayList<>());
        this.role = role;
        this.status = status;
        this.emailValidation = EmailValidation.Start();
        this.passwordUpdater = null;
    }

    public void StartChangePassword(){
        if (this.emailValidation.validated()) this.passwordUpdater = PasswordUpdater.Start();
        else throw new ValidationFailedException("Necessário validar o email primeiro");
    }

    public void ChangePassword(String token, Password newPassword){
        if (this.passwordUpdater != null && this.passwordUpdater.CheckToken(token)) {
            this.plaintext = newPassword;
            this.passwordUpdater = null;
        }
        else throw new ValidationFailedException("Não é possível alterar a senha no momento");
    }

    public void ValidateEmail(String token){
        this.emailValidation = this.emailValidation.Validate(token);
        this.status = Status.ON;
    }

    public EmailValidation getEmailValidation() {
        return emailValidation;
    }

    public void Deactivate(){
        this.status = Status.OFF;
    }

    public void Reactivate(){
        this.status = Status.ON;
    }

    public PasswordUpdater getPasswordUpdater() {
        return passwordUpdater;
    }

    public void UpdateUser(Email email, PhoneNumber phoneNumber, Address address){
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void HireUser(){
        this.role = Role.ADMIN;
    }

    public void DismissAdmin(){
        this.role = Role.COMUM;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Password getPlaintext() {
        return plaintext;
    }

    public Name getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
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

    public Cart getCart() {
        return cart;
    }

    public Status getStatus() {
        return status;
    }

}
