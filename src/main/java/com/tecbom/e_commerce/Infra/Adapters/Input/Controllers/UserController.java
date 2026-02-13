package com.tecbom.e_commerce.Infra.Adapters.Input.Controllers;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOAddCartItem;
import com.tecbom.e_commerce.Application.DTOs.Users.DTORemoveCartItem;
import com.tecbom.e_commerce.Application.DTOs.Users.DTOUpdateUser;
import com.tecbom.e_commerce.Application.UseCases.User.*;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("TecBom/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final DeleteUserUseCase deleteUserUseCase;
    private final AddCartItemUseCase addCartItemUseCase;
    private final RemoveCartItemUseCase removeCartItemUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetCartUseCase getCartUseCase;

    public UserController(DeleteUserUseCase deleteUserUseCase, AddCartItemUseCase addCartItemUseCase, RemoveCartItemUseCase removeCartItemUseCase, UpdateUserUseCase updateUserUseCase, GetCartUseCase getCartUseCase) {
        this.deleteUserUseCase = deleteUserUseCase;
        this.addCartItemUseCase = addCartItemUseCase;
        this.removeCartItemUseCase = removeCartItemUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.getCartUseCase = getCartUseCase;
    }

    @PostMapping("/cart/add")
    @PreAuthorize("#dtoAddCartItem.cpf().cpf() == authentication.principal.cpf")
    public void addCartItem(@RequestBody DTOAddCartItem dtoAddCartItem) {
        addCartItemUseCase.addCartItem(dtoAddCartItem);
    }

    @DeleteMapping("/configuration/deactivate")
    public void deactivateUserConfiguration(Cpf cpf) {
        deleteUserUseCase.deleteUserByCpf(cpf);
    }

    @DeleteMapping("/cart/remove")
    @PreAuthorize("#dtoRemoveCartItem.cpf().cpf() == authentication.principal.cpf")
    public void removeCartItem(@RequestBody DTORemoveCartItem dtoRemoveCartItem) {
        removeCartItemUseCase.removeCartItem(dtoRemoveCartItem);
    }

    @PutMapping("/configuration/update")
    @PreAuthorize("#dtoUpdateUser.cpf().cpf() == authentication.principal.cpf")
    public void updateUserConfiguration(@RequestBody DTOUpdateUser dtoUpdateUser) {
        updateUserUseCase.updateUser(dtoUpdateUser);
    }

    @GetMapping("/cart")
    @PreAuthorize("#cpf.cpf() == authentication.principal.cpf")
    public Cart getCart(@RequestBody Cpf cpf) {
        return getCartUseCase.getCart(cpf);
    }


}
