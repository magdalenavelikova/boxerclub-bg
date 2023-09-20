//package bg.boxerclub.boxerclubbgrestserver.core.config;
//
//import bg.boxerclub.boxerclubbgrestserver.service.UserService;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
//
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class AppMethodSecurityConfig extends GlobalMethodSecurityConfiguration {
//
//    private final UserService userService;
//
//    public AppMethodSecurityConfig(UserService userService) {
//        this.userService = userService;
//    }
//
//    @Override
//    protected MethodSecurityExpressionHandler createExpressionHandler() {
//        return new AppSecurityExpressionHandler(userService);
//    }
//}
