package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void deveAjustarEscalaParaDuasCasasDecimaisNoConstrutor() {
        Price price = new Price(new BigDecimal("100.5"));
        assertEquals(new BigDecimal("100.50"), price.price());
    }

    @Test
    void deveLancarExcecaoQuandoPrecoDoProdutoForMenorOuIgualAZero() {
        Price priceInvalido = new Price(new BigDecimal("0.00"));
        assertThrows(InvalidDataException.class, () -> priceInvalido.productPrice(priceInvalido));

        Price priceNegativo = new Price(new BigDecimal("-10.00"));
        assertThrows(InvalidDataException.class, () -> priceNegativo.productPrice(priceNegativo));
    }

    @Test
    void deveSomarPrecosCorretamente() {
        Price p1 = new Price(new BigDecimal("50.00"));
        Price p2 = new Price(new BigDecimal("25.50"));

        Price resultado = p1.add(p2);

        assertEquals(new BigDecimal("75.50"), resultado.price());
    }

    @Test
    void deveAplicarDescontoComSucesso() {
        Price original = new Price(new BigDecimal("100.00"));
        BigDecimal percentual = new BigDecimal("10.00");

        Price comDesconto = original.discount(percentual);

        assertEquals(new BigDecimal("90.00"), comDesconto.price());
    }

    @Test
    void deveLancarExcecaoParaPercentualDeDescontoInvalido() {
        Price price = new Price(new BigDecimal("100.00"));

        assertThrows(InvalidDataException.class, () -> price.discount(new BigDecimal("-1")));
        assertThrows(InvalidDataException.class, () -> price.discount(new BigDecimal("101")));
    }
}