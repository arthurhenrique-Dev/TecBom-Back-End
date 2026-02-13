package com.tecbom.e_commerce.Application.UseCases.User;

import com.tecbom.e_commerce.Application.Ports.Output.CartRepository;
import com.tecbom.e_commerce.Domain.Entities.Users.Cart;
import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.UserNotFoundException;
import com.tecbom.e_commerce.Domain.ValueObjects.Cpf;
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

class GetCartUseCaseTest {

    @Mock
    private CartRepository repository;

    @InjectMocks
    private GetCartUseCase getCartUseCase;

    private Cart cart;

     @BeforeEach
     void setUp() {
         MockitoAnnotations.initMocks(this);
         cart = new ReferenceObject().fullCart();
     }

     @Test
     @DisplayName("Deve retornar o carrinho do usuário com sucesso")
     void shouldGetCart() {
         when(repository.getCart(cart.getCpf())).thenReturn(Optional.of(cart));
         getCartUseCase.getCart(cart.getCpf());
         verify(repository, times(1)).getCart(any(Cpf.class));
         assertNotNull(cart);
     }

     @Test
     @DisplayName("Deve lançar UserNotFoundException quando o carrinho do usuário não for encontrado")
     void shouldThrowUserNotFoundException() {
         when(repository.getCart(cart.getCpf())).thenReturn(Optional.empty());
         assertThrows(UserNotFoundException.class, () -> getCartUseCase.getCart(cart.getCpf()));
         verify(repository, times(1)).getCart(any(Cpf.class));
     }
}