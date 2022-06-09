package tech.developerdhairya.securityclient.Util;


import org.springframework.stereotype.Component;
import tech.developerdhairya.securityclient.Entity.VerificationTokenEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

@Component
public class AuthenticationUtil {


    public String getApplicationUrl(HttpServletRequest httpServletRequest){
        System.out.println(httpServletRequest.getServletPath().toString());;
        return "http://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+"/"+httpServletRequest.getContextPath();
    }

    public Date calculateExpirationTime(int expirationTime){
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());       //Setting calender time
        calendar.add(Calendar.MINUTE,expirationTime);        //Adding EXPIRATION_TIME to total time
        return calendar.getTime();
    }

    public boolean checkTokenExpiry(VerificationTokenEntity token){
        Calendar calendar=Calendar.getInstance();
        return token.getExpirationTime().getTime()>calendar.getTime().getTime();
    }

}
