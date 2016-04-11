package com.formento.cadastro.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

// https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
@ControllerAdvice
public class CadastroExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CadastroExceptionDefault.class)
    @ResponseBody
    public CadastroError handleBadRequest(CadastroException e) {
        return e.generateCadastroError();
    }

    // 401 Unauthorized:
    // If the request already included Authorization credentials, then the 401 response indicates that authorization has been refused for those credentials.
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedCadastroExceptionDefault.class)
    @ResponseBody
    public CadastroError handleUnauthorized(UnauthorizedCadastroExceptionDefault e) {
        return e.generateCadastroError();
    }

    // 401 Unauthorized:
    // If the request already included Authorization credentials, then the 401 response indicates that authorization has been refused for those credentials.
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InternalAuthenticationServiceException.class)
    @ResponseBody
    public CadastroError handleUnauthorized(InternalAuthenticationServiceException e) {
        return new CadastroErrorDefault(e.getMessage());
    }

    // 403 Forbidden:
    // The server understood the request, but is refusing to fulfill it.
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedCadastroExceptionDefault.class)
    @ResponseBody
    public CadastroError handleForbidden(AccessDeniedCadastroExceptionDefault e) {
        return e.generateCadastroError();
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler(BusinessCadastroExceptionDefault.class)
    @ResponseBody
    public CadastroError handleBadRequest(BusinessCadastroExceptionDefault e) {
        return e.generateCadastroError();
    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MyBadDataException.class)
//    @ResponseBody ErrorInfo handleBadRequest(HttpServletRequest req, Exception ex) {
//        return new ErrorInfo(req.getRequestURL(), ex);
//    }

}
