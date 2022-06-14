package tech.developerdhairya.securityclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tech.developerdhairya.securityclient.Service.SecurityService;
import tech.developerdhairya.securityclient.Util.JWTUtil;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {

    @Autowired
    SecurityService securityService;

    @Autowired
    JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        SecurityContext context= SecurityContextHolder.getContext();
        String authorizationHeader=request.getHeader("Authorization");
        String jwt=null;
        String username=null;

        if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer")){
            jwt=authorizationHeader.substring(7);
            username=jwtUtil.getUsernameFromToken(jwt);
        }

        if(context.getAuthentication()==null && username!=null){

            UserDetails userDetails=securityService.loadUserByUsername(username);
            if(jwtUtil.validateToken(jwt,userDetails)){
                //Create and set username password authentication token
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request,response);
        }



    }
}
