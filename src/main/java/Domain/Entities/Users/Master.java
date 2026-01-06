package Domain.Entities.Users;

import Domain.ValueObjects.*;

public class Master {

    private final Cpf cpf;
    private final Name name;
    private Password password;
    private Email email;
    private PhoneNumber phoneNumber;
    private Role role;

    public Master(Cpf cpf, Name name, Password password, Email email, PhoneNumber phoneNumber) {
        this.cpf = cpf;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = Role.MASTER;
    }


}
