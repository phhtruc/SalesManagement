package com.skyline.SalesManager.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ResponseError{

    private Date timestamp;
    private int status;
    private String part;
    private String error;
    private String message;

}
