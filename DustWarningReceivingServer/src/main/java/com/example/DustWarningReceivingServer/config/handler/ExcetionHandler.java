package com.example.DustWarningReceivingServer.config.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.DustWarningReceivingServer.model.error.UserDefinedException;
import com.example.DustWarningReceivingServer.model.vo.ApiErrorResultVo;


@RestControllerAdvice(basePackages = "com.example.DustWarningReceivingServer")
public class ExcetionHandler{
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
		e.printStackTrace();
		ApiErrorResultVo apiError = new ApiErrorResultVo();
		e.getBindingResult().getAllErrors().stream().forEach(error -> apiError.getMsgs().add(error.getDefaultMessage()));
		apiError.setStatus(HttpStatus.BAD_REQUEST.value());
		apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(e.getStatusCode()).body(apiError);
	}

	@ExceptionHandler(UserDefinedException.class)
	public ResponseEntity<?> MethodArgumentNotValidExceptionHandler(UserDefinedException e){
		e.printStackTrace();
		ApiErrorResultVo apiError = new ApiErrorResultVo();
		apiError.getMsgs().add(e.getMessage());
		apiError.setStatus(HttpStatus.BAD_REQUEST.value());
		apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}

	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<?> MethodArgumentNotValidExceptionHandler(UsernameNotFoundException e){
		e.printStackTrace();
		ApiErrorResultVo apiError = new ApiErrorResultVo();
		apiError.getMsgs().add(e.getMessage());
		apiError.setStatus(HttpStatus.BAD_REQUEST.value());
		apiError.setHttpStatus(HttpStatus.BAD_REQUEST);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
}