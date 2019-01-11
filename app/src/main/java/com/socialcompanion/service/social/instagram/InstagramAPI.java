package com.socialcompanion.service.social.instagram;

public class InstagramAPI {

    private static final String LOGIN_URL = "https://www.instagram.com/oauth/authorize/?client_id=43b993c419f94064b25ed722408807d9&redirect_uri=https://www.google.com&response_type=token";
    private static final String REDIRECT_URL = "https://www.google.com";

    public static String getLoginUrl() {
        return LOGIN_URL;
    }

    public static String getRedirectUrl() {
        return REDIRECT_URL;
    }
}
