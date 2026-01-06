package Application.Ports.Input.User;

import Domain.ValueObjects.Cpf;

public interface UpdatePasswordUserPort {

    void updatePassword(Cpf cpf);
}
