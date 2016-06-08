package main.java.com.fang.dto;
/**
 * 封装json结果
 * @author Jameslin
 *
 * @param <T>
 */
public class SeckillResult<T> {
	private boolean isSuccess;
	private T data;
	private String error;
	public SeckillResult(boolean isSuccess, T data) {
		super();
		this.isSuccess = isSuccess;
		this.data = data;
	}
	public SeckillResult(boolean isSuccess, String error) {
		super();
		this.isSuccess = isSuccess;
		this.error = error;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
