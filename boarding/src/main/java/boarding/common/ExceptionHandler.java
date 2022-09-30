package boarding.common;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
@ControllerAdvice // 본클래스가 예외처리 클래스임을 알려준다.

public class ExceptionHandler {

private Logger log = LoggerFactory.getLogger(this.getClass());
@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)

//해당 메서드에서 처리할 예외를 지정한다. 실제 프로제그에서는 다양한 예외를 처리하기 위한 각가의 예외처리가 필요하다.

//NullPointException , NumberFormatException등 자바의 기본 예외 및 프로젝트에 필요한 커스텀 예외를 포함하여

//각가의 예외에 맞는 적절한 예외처리가 필요하다.

//여러개의 예외처리 메서드를 추가할때 자바의 모든 예외는 Exception  클래스를 상속받는다.

//Exception.class를 처리하는 메서드는 가장 마지막에 있어야한다.

public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception) {

ModelAndView mv = new ModelAndView("/error/error_default");// 예외발생시 보여줄 화면 지정

mv.addObject("exception",exception);

log.error("exception",exception); //에러로그 출력

return mv;
}
}

