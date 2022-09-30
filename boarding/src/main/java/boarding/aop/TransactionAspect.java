package boarding.aop;

import java.util.Collections;
import org.springframework.aop.Advisor;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import org.springframework.aop.support.DefaultPointcutAdvisor;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;

import org.springframework.transaction.interceptor.RollbackRuleAttribute;

import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;

import org.springframework.transaction.interceptor.TransactionInterceptor;

@Configuration
public class TransactionAspect {
	
	//트랜잭션을 설정할때 사용되는 설정값을 상수로 선언한다.
	private static final String AOP_TRANSACTION_METHOD_NAME = "*";
	private static final String AOP_TRANSACTION_EXPRESSION= "execution(* board..service.*Impl.*(..))";

	@Autowired 
	private PlatformTransactionManager transactionManager;
	

@Bean
public TransactionInterceptor transactionAdvice() {
	MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
	
	RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
	
	transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);// 트랜잭션의 이름을 설정한다. 

     // 트랜잭션 모니터에서 트랜잭션의 이름으로 호가인할 수 있다.

		transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
		
		//트랜잭션에서 롤백을 하는 룰을 설정한다. //여기서는 예외가 일어나면 롤백이 수행되도록 지정되었다.
		
		//Exception.class를 롤백의 룰로 등록하면 자바의 모든 예외는 Exception 클래스를 상속받기 때문에 어떠한 예외가 발생해도 롤백이 수행된다.
		
		source.setTransactionAttribute(transactionAttribute);
		return new TransactionInterceptor(transactionManager, source);

}

		@Bean
		public Advisor transactionAdviceAdvisor() {
			
		AspectJExpressionPointcut  pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);///AOP의 포인트컷을 설정한다
		
		//여기서는 비지니스로직이 수행되는 모든 ServiceImpl 클래스의 모든 메서드를 지정했다.
		
		return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
		
		}
}