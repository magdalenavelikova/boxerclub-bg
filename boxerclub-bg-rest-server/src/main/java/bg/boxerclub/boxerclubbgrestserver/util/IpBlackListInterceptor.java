package bg.boxerclub.boxerclubbgrestserver.util;

import bg.boxerclub.boxerclubbgrestserver.service.BlackListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class IpBlackListInterceptor implements HandlerInterceptor {

    private final BlackListService service;

    public IpBlackListInterceptor(BlackListService service) {
        this.service = service;
    }


    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) {
        String ip = getIpAddressFromRequest(request);
        int lastDotIndex = ip.lastIndexOf('.');
        String ipArea;
        if (lastDotIndex != -1) {
            ipArea = ip.substring(0, lastDotIndex);
        } else {
            ipArea = ip;
        }

        if (service.isBlacklisted(ipArea)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());

            return false;
        }

        return true;
    }

    private String getIpAddressFromRequest(HttpServletRequest request) {

        String ipAddress = null;

        String xffHeader = request.getHeader("X-Forwarded-For");
        if (xffHeader != null && !xffHeader.equals("unknown")) {
            int commaIdx = xffHeader.indexOf(",");
            if (commaIdx > 0) {
                ipAddress = xffHeader.substring(0, commaIdx - 1);
            }
        }
        // String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_FORWARDED_FOR");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_FORWARDED");
        }

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }


        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }
}