package br.com.cambio_service.cambio.configuration.authentication;

import br.com.cambio_service.cambio.configuration.AssinaturaValidacao;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.auth.login.CredentialException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.List;

@Configuration
public class CustomFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;
    private final AssinaturaValidacao assinaturaValidacao;

    public CustomFilter(JwtDecoder jwtDecoder,
                        AssinaturaValidacao assinaturaValidacao) {
        this.jwtDecoder = jwtDecoder;
        this.assinaturaValidacao = assinaturaValidacao;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        assinaturaValida(request);

        String token = request.getHeader("Authorization-security");
        if (StringUtils.isNotBlank(token)) {
            Jwt decoded = jwtDecoder.decode(token);
            List<GrantedAuthority> permissionProject = decoded.getClaimAsStringList("scope").stream()
                    .<GrantedAuthority>map(SimpleGrantedAuthority::new)
                    .toList();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(decoded.getSubject(), null, permissionProject);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private void assinaturaValida(HttpServletRequest request) {
        try {
            String assinatura = request.getHeader("X-Signature");
            boolean assinaturaValida = assinaturaValidacao.valido(assinatura);
            if (!assinaturaValida) {
                throw new CredentialException("Invalid Credentials!");
            }
        } catch (CredentialException | SignatureException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
