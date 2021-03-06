package com.mixoor.khademni.exception;

import com.sun.xml.internal.ws.developer.MemberSubmissionAddressing;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message ){
        super(message);
    }

}
