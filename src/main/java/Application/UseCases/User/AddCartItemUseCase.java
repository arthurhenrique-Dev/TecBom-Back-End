package Application.UseCases.User;

import Application.DTOs.Users.DTOAddCartItem;
import Application.Ports.Input.User.AddCartItemPort;
import Application.Ports.Output.UserRepository;
import Domain.Entities.Users.User;
import Domain.Exceptions.Exceptions.UserNotFoundException;
import Domain.Exceptions.Exceptions.ValidationFailedException;

public class AddCartItemUseCase implements AddCartItemPort {

    private final UserRepository repository;

    public AddCartItemUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addCartItem(DTOAddCartItem dtoAddCartItem) {
        User user = repository.getUserByCpf(dtoAddCartItem.cpf())
                .orElseThrow(() -> new UserNotFoundException());
        user.getCart().addCartItem(dtoAddCartItem.cartItem());
        repository.saveUser(user);
    }
}
