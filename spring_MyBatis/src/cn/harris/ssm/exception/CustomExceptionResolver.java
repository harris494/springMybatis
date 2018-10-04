package cn.harris.ssm.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理器
 * 
 * @author harri
 *
 */
public class CustomExceptionResolver implements HandlerExceptionResolver {
	/**
	 * （非 Javadoc）
	 * <p>
	 * Title: resolveException
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 *            系统 抛出的异常
	 * @return
	 * @see HandlerExceptionResolver#resolveException(HttpServletRequest,
	 *      HttpServletResponse, Object, Exception)
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// handler就是处理器适配器要执行Handler对象（只有method）
		// 解析出异常类型
		// 如果该 异常类型是系统 自定义的异常，直接取出异常信息，在错误页面展示
		// String message = null;
		// if(ex instanceof CustomException){
		// message = ((CustomException)ex).getMessage();
		// }else{
		// 如果该 异常类型不是系统 自定义的异常，构造一个自定义的异常类型（信息为“未知错误”）
		// message="未知错误";
		// }
		
		//上面代码变为
		CustomException customException = null;
		if(ex instanceof CustomException) {
			customException = (CustomException)ex;
		}else {
			customException = new CustomException("Unknown Failure!");
		}
		
		String message = customException.getMessage();
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", message);
		modelAndView.setViewName("error");

		return modelAndView;
	}

}
