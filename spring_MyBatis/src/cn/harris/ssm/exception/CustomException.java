package cn.harris.ssm.exception;
/**
 * 系统自定义异常类,针对预期的异常，需要在程序中抛出此类的异常 
 * @author harri
 *
 */
public class CustomException extends Exception{
	//异常信息
	private String message;

	public CustomException(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
