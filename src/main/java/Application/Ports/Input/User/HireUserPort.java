package Application.Ports.Input.User;

import Domain.ValueObjects.Cpf;

public interface HireUserPort {

    void hireUser(Cpf cpf);
}
