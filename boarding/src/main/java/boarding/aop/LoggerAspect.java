package boarding.aop;
import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.Around;

import org.aspectj.lang.annotation.Aspect;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

@Component

@Aspect //자바코드에서 AOP를 설정한다.
public class LoggerAspect {

private Logger log = LoggerFactory.getLogger(this.getClass());
@Around(" execution(* boarding..controller.*Controller.*(..)) or execution(* boarding..service.*Impl.*(..)) or execution(* boarding..dao.*Mapper.*(..))") 

//@Around 어노테이션으로 해당 기능이 실행될 시점, 어드바이스를 정의한다. / execution은 포인트컷 표현식으로 적용할 메서드를 명시할때 사용된다.

public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{

String type = "";

// 실행되는 메서드의 이름을 이용해서 컨트롤러 서비스 매퍼를 구분한 후 실행되는 메서드의 이름을 출력한다.

String name = joinPoint.getSignature().getDeclaringTypeName();

if(name.indexOf("Controller") > -1) {

type = "Controller \t:";

}else if (name.indexOf("Service") > -1) {

type ="ServiceImpl \t:"; 

}else if (name.indexOf("Mapper") > -1) {

type="Mapper \t\t:";

}
log.debug(type +name +"." + joinPoint.getSignature().getName() + "()");

return joinPoint.proceed();

}

}

