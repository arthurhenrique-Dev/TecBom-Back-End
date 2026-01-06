package Application.Ports.Input.User;

import Domain.ValueObjects.Cpf;

public interface ReactivateUserPort {

    void reactivateUser(Cpf cpf);
}
