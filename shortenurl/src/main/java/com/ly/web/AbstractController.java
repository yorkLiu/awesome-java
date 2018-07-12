package com.ly.web;

import com.ly.data.ApiResponse;
import com.ly.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by yongliu on 7/12/18.
 */
public class AbstractController {
  
  @CrossOrigin(allowedHeaders = "foo", origins = "*")
  @ResponseStatus(HttpStatus.OK)
  @ExceptionHandler(NotFoundException.class)
  public ApiResponse exception(NotFoundException e){
    return new ApiResponse(ApiResponse.ERROR, e.getMessage());
  }
}
