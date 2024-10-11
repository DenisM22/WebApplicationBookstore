package allClasses.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Aspect
public class MainAspect {

    private static final Logger logger = LoggerFactory.getLogger(MainAspect.class);

    @Before("execution (public * allClasses.services.BooksService.*(..))")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("Вызов метода " + joinPoint.getSignature().getName());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Пользователь с именем " + auth.getName() + " и ролью " + auth.getAuthorities());
    }

}
