package nextstep.subway.auth.infrastructure;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;

public class AuthorizationExtractor {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_TYPE = "Bearer";
    public static final String ACCESS_TOKEN_TYPE =
        AuthorizationExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

    private AuthorizationExtractor() {
    }

    public static String extract(final HttpServletRequest request) {
        final Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while (headers.hasMoreElements()) {
            String value = headers.nextElement();
            if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
                String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
                request.setAttribute(
                    ACCESS_TOKEN_TYPE,
                    value.substring(0, BEARER_TYPE.length()).trim()
                );
                int commaIndex = authHeaderValue.indexOf(',');
                if (commaIndex > 0) {
                    authHeaderValue = authHeaderValue.substring(0, commaIndex);
                }
                return authHeaderValue;
            }
        }

        return null;
    }
}
