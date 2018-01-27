package cn.e3mall.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
/**
 * 全局异常处理器
 * @author LiuTaiwen
 *
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {
		//写日志
		logger.debug("----------------您已经进入全局异常处理器----------------");
		logger.debug(handler.getClass().toString());
		logger.debug("--------------------发生了异常---------------------");
		logger.info("你已经进入全局异常处理器");
		logger.error("系统发生异常", e);
		//展示错误的页面
		ModelAndView andView = new ModelAndView();
		andView.setViewName("error/exception");
		return andView;
	}

}
