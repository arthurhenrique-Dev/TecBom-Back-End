package com.tecbom.e_commerce.Domain.Entities.Users;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;
import com.tecbom.e_commerce.ReferenceObjects.ReferenceObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MasterTest {

    private Master master;

    @BeforeEach
    void setUp() {
        master = new ReferenceObject().NormalMaster();
    }

    @Test
    @DisplayName("Deve desativar um master ativo")
    void shouldTurnMasterOff() {
        if (master.getStatus() == Status.OFF) master.masterOn();

        master.masterOff();

        assertEquals(Status.OFF, master.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar desativar um master já inativo")
    void shouldThrowExceptionWhenAlreadyOff() {
        master.masterOff();

        assertThrows(InvalidDataException.class, () -> master.masterOff());
    }

    @Test
    @DisplayName("Deve ativar um master inativo")
    void shouldTurnMasterOn() {
        master.masterOff();

        master.masterOn();

        assertEquals(Status.ON, master.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar ativar um master já ativo")
    void shouldThrowExceptionWhenAlreadyOn() {
        if (master.getStatus() == Status.OFF) master.masterOn();

        assertThrows(InvalidDataException.class, () -> master.masterOn());
    }
}