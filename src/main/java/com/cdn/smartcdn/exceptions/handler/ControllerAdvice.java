package com.cdn.smartcdn.exceptions.handler;

import java.util.Arrays;

import com.cdn.smartcdn.dto.response.GenericErrorResponse;
import com.cdn.smartcdn.exceptions.BaseException;
import com.cdn.smartcdn.exceptions.ImageNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(BaseException exception) {
        log.error("[SERVICE] Error encountered with message: {}, info: {}, exception: {}",
                exception.getMessage(), gatherInfo(exception.getInfo()), exception.gatherStackTrace());

        return new ResponseEntity<>(GenericErrorResponse
                .builder()
                .reason(exception.getMessage())
                .build(), exception.getHttpStatus());
    }

    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<?> handleImageNotFoundException(ImageNotFoundException exception) {
        log.error("[SERVICE] Error encountered with message: {}, info: {}, exception: {}",
                exception.getMessage(), gatherInfo(exception.getInfo()), exception.gatherStackTrace());

        return new ResponseEntity<>(GenericErrorResponse
                .builder()
                .reason(exception.getMessage())
                .build(), exception.getHttpStatus());
    }

    private String gatherInfo(String... info) {
        if (ArrayUtils.isEmpty(info)) {
            return "None";
        }
        return StringUtils.join(Arrays.asList(info), ", ");
    }

}
