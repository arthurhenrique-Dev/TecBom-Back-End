package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.DTOs.Users.DTORemoveCartItem;
import com.tecbom.e_commerce.Application.Ports.Output.CartRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.CartItem;
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

class RemoveCartItemUseCaseTest {

    @Mock
    private CartRepository repository;

    @InjectMocks
    private RemoveCartItemUseCase removeCartItemUseCase;

    private Cart cart;

    private CartItem cartItem;

    private DTORemoveCartItem dtoRemoveCartItem;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cart = new ReferenceObject().fullCart();
        dtoRemoveCartItem = new ReferenceObject().dtoRemoveCartItem();
        cartItem = new ReferenceObject().simpleItem();
    }

    @Test
    @DisplayName("Deve remover um item do carrinho do usuário com sucesso")
    void sucessRemoveCartItem() {
        cart.addCartItem(cartItem);
        assertEquals(cart.getItems().size(), 1);
        when(repository.getCart(dtoRemoveCartItem.cpf())).thenReturn(Optional.of(cart));
        removeCartItemUseCase.removeCartItem(dtoRemoveCartItem);
        verify(repository, times(1)).getCart(dtoRemoveCartItem.cpf());
        verify(repository, times(1)).saveCart(cart);
        assertEquals(cart.getItems().size(), 0);
    }

    @Test
    @DisplayName("Deve lançar UserNotFoundException quando o carrinho do usuário não for encontrado")
    void shouldThrowUserNotFoundException() {
        when(repository.getCart(dtoRemoveCartItem.cpf())).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> removeCartItemUseCase.removeCartItem(dtoRemoveCartItem));
        verify(repository, times(1)).getCart(dtoRemoveCartItem.cpf());
        verify(repository, times(0)).saveCart(any(Cart.class));
        assertEquals(cart.getItems().size(), 0);
    }

    @Test
    @DisplayName("Deve lançar InvalidDataException quando o índice do item a ser removido for inválido")
    void shouldThrowInvalidDataException() {
        cart.addCartItem(cartItem);
        assertEquals(cart.getItems().size(), 1);
        DTORemoveCartItem invalidDto = new DTORemoveCartItem(dtoRemoveCartItem.cpf(), -1);
        when(repository.getCart(dtoRemoveCartItem.cpf())).thenReturn(Optional.of(cart));
        assertThrows(InvalidDataException.class, () -> removeCartItemUseCase.removeCartItem(invalidDto));
        verify(repository, times(1)).getCart(dtoRemoveCartItem.cpf());
        verify(repository, times(0)).saveCart(any(Cart.class));
        assertEquals(cart.getItems().size(), 1);
    }
}