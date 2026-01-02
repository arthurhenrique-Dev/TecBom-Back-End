package Domain.ValueObjects;

import Domain.Exceptions.InvalidDataException;

public record ValidText(String text) {

    public ValidText(String text){
        if (text != null && !text.trim().isEmpty()) this.text = text;
        else throw new InvalidDataException("Texto inválido");
    }
}
