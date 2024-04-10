package com.likevel.kaloriinnhold.exception;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class ExceptionErrorMessage {
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
