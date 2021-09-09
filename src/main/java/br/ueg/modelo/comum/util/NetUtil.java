package br.ueg.modelo.comum.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class NetUtil {
    /**
     * @return Retorna o ip da requisição
     */
    public static String getClientIpAddress(){
        if (RequestContextHolder.getRequestAttributes() == null) {
            return "0.0.0.0";
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();

        return  request.getRemoteAddr();
    }
}
