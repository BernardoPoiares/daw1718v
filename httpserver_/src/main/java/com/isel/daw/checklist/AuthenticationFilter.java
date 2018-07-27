package com.isel.daw.checklist;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;


public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    static final String HEADER_STRING = "Authorization";
    static final String SECRET = "secret";
    static final String TOKEN_PREFIX = "Bearer ";
    static final String INTROSPECTION_ENDPOINT_URL="http://localhost:8080/openid-connect-server-webapp/introspect";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String headerAuth = ((HttpServletRequest)request).getHeader("Authorization");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization","Bearer eyJraWQiOiJyc2ExIiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhZG1pbiIsImF6cCI6ImNsaWVudCIsImlzcyI6Imh0dHA6XC9cL2xvY2FsaG9zdDo4MDgwXC9vcGVuaWQtY29ubmVjdC1zZXJ2ZXItd2ViYXBwXC8iLCJleHAiOjE1MzI2NTc0NjYsImlhdCI6MTUzMjY1Mzg2NiwianRpIjoiOWQ0MmUzZDAtZDI4ZS00MmU3LThmOWMtZDlmMjg0NjE2YjcxIn0.QASJxzamyEIyDzUAzlbencH-yT74C_ed-wxAY2mg_e9t44zBkpPZjcHyxo1q-ecIsM3uWfeQ2qHeB90sENbg24jLkhdeNOcpz-v3YLPOSjTqqMqDdCVk8CYacL6-veNGw1xfWlZagXzkKOjlrNl-iQXFwpeoYctrlV65EKVP_iTv_qx78SkZFbG5In_bcP9XoVWXqJsR1PO-EqW_Q4_tQeT494f-xQfrVyAPJ8fiH37BOuAsseXFe-gjL3kRNEzMDf3ilsMlzy87jLtDkc3iUCTiz3qmQvTgw8-PVGBE_Dnn9t0OaXPXR0AMdgNRdeCvpJxGLTej0qBTUW076q0bRQ");

        HttpEntity<String> entity = new HttpEntity<>( headers);
        ResponseEntity<String> resttemp_response=
                restTemplate.exchange(INTROSPECTION_ENDPOINT_URL, HttpMethod.GET,entity, String.class);

        filterChain.doFilter(request, response);
    }

    /*@Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        OAuth2AccessToken accessToken;
        try {
            accessToken = restTemplate.getAccessToken();
        } catch (OAuth2Exception e) {
            throw new BadCredentialsException("Could not obtain access token", e);
        }
        try {
            String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
            String kid = JwtHelper.headers(idToken).get("kid");
            Jwt tokenDecoded = JwtHelper.decodeAndVerify(idToken, verifier(kid));
            Map<String, String> authInfo = new ObjectMapper()
                    .readValue(tokenDecoded.getClaims(), Map.class);
            verifyClaims(authInfo);
            OpenIdConnectUserDetails user = new OpenIdConnectUserDetails(authInfo, accessToken);
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        } catch (InvalidTokenException e) {
            throw new BadCredentialsException("Could not obtain user details from token", e);
        }
        OpenIdConnectUserDetails user = new OpenIdConnectUserDetails(null, null);

        return new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
    }
*/
    //RequestMatcher matcher=new AntPathRequestMatcher("/**");

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher("/**"));
        setAuthenticationManager(authenticationManager);

    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        AccountCredentials creds = new ObjectMapper()
                .readValue(request.getInputStream(), AccountCredentials.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        Collections.emptyList())

        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
       String a="";


    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
    }


}
