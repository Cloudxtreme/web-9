package cn.netkiller.component;

import org.springframework.stereotype.Component;

@Component
@Aspect
public class NeoLoggerAspect {
	@Pointcut("@annotation(cn.netkiller.annotation.NeoLogger)")
    private void cut() { }

    @Around("cut()")
    public void advice(ProceedingJoinPoint joinPoint){
        System.out.println("@Around Begin");
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("@Around End");
    }

    @Before("cut()")
    public void before() {
        this.printLog("@Before");
    }

    @After("recordLog()")
    public void after() {
        this.printLog("@After");
    }
}
