package Application.Ports.Input.User;

import Domain.ValueObjects.Cpf;

public interface DissmissAdminPort {

    void dissmissAdmin(Cpf cpf);
}
