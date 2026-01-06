package Application.UseCases.User;

import Application.DTOs.Users.DTORemoveCartItem;
import Application.Ports.Input.User.RemoveCartItemPort;
import Application.Ports.Output.UserRepository;
import Domain.Entities.Users.User;
import Domain.Exceptions.Exceptions.ValidationFailedException;

public class RemoveCartItemUseCase implements RemoveCartItemPort {

    private final UserRepository repository;

    public RemoveCartItemUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void removeCartItem(DTORemoveCartItem dtoRemoveCartItem) {
        User user = repository.getUserByCpf(dtoRemoveCartItem.cpf())
                .orElseThrow(() -> new ValidationFailedException("Usuário fora de sessão"));
        user.getCart().removeCartItem(dtoRemoveCartItem.idxItem());
        repository.saveUser(user);
    }
}
