package main.java.com.fang.exception;
/**
 * 秒杀关闭异常
 * @author Jameslin
 *
 */
public class SeckillClosedException extends SeckillException {

	public SeckillClosedException() {
		super();
	}

	public SeckillClosedException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillClosedException(String message) {
		super(message);
	}
	
}
