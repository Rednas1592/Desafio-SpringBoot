package br.com.desafio.validation;

import java.util.concurrent.ExecutionException;

import javax.persistence.EntityNotFoundException;
import javax.validation.UnexpectedTypeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErroHandler {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ErrorResponse handle(MethodArgumentNotValidException exception){
		
		return new ErrorResponse("400", exception.getMessage());
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private ErrorResponse handle(HttpRequestMethodNotSupportedException exception){
		
		return new ErrorResponse("400", exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	private ErrorResponse handle(HttpMessageNotReadableException exception){
		
		return new ErrorResponse("400", exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NullPointerException.class)
	private ErrorResponse handle(NullPointerException exception){
		
		return new ErrorResponse("400", "Null Pointer");
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	private ErrorResponse handle(IllegalArgumentException exception){
		
		return new ErrorResponse("400", exception.getMessage());
	}	
	

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotWritableException.class)
	private ErrorResponse handle(HttpMessageNotWritableException exception){
		
		return new ErrorResponse("400", exception.getMessage());
	}	
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EntityNotFoundException.class)
	private ErrorResponse handle(EntityNotFoundException exception){
		
		return new ErrorResponse("400", exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(UnexpectedTypeException.class)
	private ErrorResponse handle(UnexpectedTypeException exception){
		
		return new ErrorResponse("400", exception.getMessage());
	}	
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ExecutionException.class)
	private ErrorResponse handle(ExecutionException exception){
		
		return new ErrorResponse("400", exception.getMessage());
	}	
}
