package main.java.com.fang.exception;

/**
 * 秒杀通用异常
 * @author Jameslin
 *
 */
public class SeckillException extends RuntimeException {

	public SeckillException() {
		super();
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillException(String message) {
		super(message);
	}
	
}
