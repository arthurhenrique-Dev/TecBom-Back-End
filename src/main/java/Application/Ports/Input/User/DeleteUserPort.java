package Application.Ports.Input.User;

import Domain.ValueObjects.Cpf;

public interface DeleteUserPort {

    void deleteUserByCpf(Cpf cpf);
}
