package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTOAddCartItem;
import com.tecbom.e_commerce.Application.Ports.Output.CartRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddCartItemUseCaseTest {

    @Mock
    private CartRepository repository;

    @InjectMocks
    private AddCartItemUseCase addCartItemUseCase;

    private DTOAddCartItem dtoAddCartItem;
    private Cart cart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        dtoAddCartItem = new ReferenceObject().dtoAddCartItem();
        cart = new ReferenceObject().fullCart();
    }
    @Test
    @DisplayName("Deve adicionar um item ao carrinho do usuário")
    void shouldAddCartItem() {

        when(repository.getCart(dtoAddCartItem.cpf())).thenReturn(Optional.of(cart));
        addCartItemUseCase.addCartItem(dtoAddCartItem);
        verify(repository, times(1)).getCart(dtoAddCartItem.cpf());
        verify(repository, times(1)).saveCart(cart);
        assertEquals(cart.getItems().get(0).getId(), dtoAddCartItem.cartItem().getId());
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o carrinho do usuário não for encontrado")
    void shouldThrowUserNotFoundException() {
        when(repository.getCart(dtoAddCartItem.cpf())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> addCartItemUseCase.addCartItem(dtoAddCartItem));
        verify(repository, times(1)).getCart(dtoAddCartItem.cpf());
        verify(repository, times(0)).saveCart(any(Cart.class));
        assertEquals(cart.getItems().contains(dtoAddCartItem.cartItem()), false);
    }
}