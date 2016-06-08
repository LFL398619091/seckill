package main.java.com.fang.exception;
/**
 * 重复秒杀异常
 * @author Jameslin
 *
 */
public class RepeatKillException extends SeckillException {

	public RepeatKillException() {
		super();
	}

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepeatKillException(String message) {
		super(message);
	}

}
