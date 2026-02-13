package com.tecbom.e_commerce.Domain.ValueObjects;

import com.tecbom.e_commerce.Domain.Exceptions.Exceptions.InvalidDataException;

public record Address(

        Cep cep,
        String rua,
        String bairro,
        String cidade,
        String estado,
        String complemento,
        ValidText numero
) {

    public Address(
            Cep cep,
            String rua,
            String bairro,
            String cidade,
            String estado,
            String complemento,
            ValidText numero
    ) {
        if (cep != null && !cep.cep().trim().isEmpty() ||
                rua != null && !rua.trim().isEmpty() ||
                cidade != null && !cidade.trim().isEmpty() ||
                bairro != null && !bairro.trim().isEmpty() ||
                estado != null && !estado.trim().isEmpty() ||
                numero != null && !numero.text().trim().isEmpty()) {
            this.cep = cep;
            this.rua = rua;
            this.bairro = bairro;
            this.cidade = cidade;
            this.estado = estado;
            this.complemento = complemento;
            this.numero = numero;
        } else throw new InvalidDataException();
    }
}
