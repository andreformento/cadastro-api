package com.formento.cadastro.exception;

public class CadastroExceptionDefault extends RuntimeException implements CadastroException {

    public CadastroExceptionDefault(String message) {
        super(message);
    }

    @Override
    public CadastroError generateCadastroError() {
        return new CadastroErrorDefault(getMessage());
    }

}
