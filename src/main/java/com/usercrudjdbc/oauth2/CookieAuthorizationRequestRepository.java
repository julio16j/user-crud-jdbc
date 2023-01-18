package com.usercrudjdbc.oauth2;

@Component
public class CookieAuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

    private static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    private static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private final CookieOperations cookieOperations;

    public CookieAuthorizationRequestRepository(CookieOperations cookieOperations) {
        this.cookieOperations = cookieOperations;
    }

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        Map<String, String> map = cookieOperations.get(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        if (map != null) {
            return OAuth2AuthorizationRequest.fromMap(map);
        }
        return null;
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
            HttpServletResponse response) {
        if (authorizationRequest == null) {
            cookieOperations.remove(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            cookieOperations.remove(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }

        cookieOperations.put(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                authorizationRequest.getAuthorizationRequest().getRequestParameters());
        cookieOperations.put(response, REDIRECT_URI_PARAM_COOKIE_NAME,
                Collections.singletonMap("redirect_uri", authorizationRequest.getAuthorizationRequest().getRedirectUri()));
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        OAuth2AuthorizationRequest oAuth2AuthorizationRequest = loadAuthorizationRequest(request);
        cookieOperations.remove(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        cookieOperations.remove(request, REDIRECT_URI_PARAM_COOKIE_NAME);
        return oAuth2AuthorizationRequest;
    }
}

