package gift.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.security.oauth2.client")
public class KakaoProperties {

    private Registration registration = new Registration();
    private Provider provider = new Provider();

    public Registration getRegistration() {
        return registration;
    }

    public Provider getProvider() {
        return provider;
    }

    public static class Registration {
        private Kakao kakao = new Kakao();

        public Kakao getKakao() {
            return kakao;
        }

        public static class Kakao {
            private String clientId;
            private String redirectUri;

            public String getClientId() {
                return clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getRedirectUri() {
                return redirectUri;
            }

            public void setRedirectUri(String redirectUri) {
                this.redirectUri = redirectUri;
            }
        }
    }

    public static class Provider {
        private Kakao kakao = new Kakao();

        public Kakao getKakao() {
            return kakao;
        }

        public static class Kakao {
            private String authorizationUri;
            private String tokenUri;
            private String userInfoUri;

            public String getAuthorizationUri() {
                return authorizationUri;
            }

            public void setAuthorizationUri(String authorizationUri) {
                this.authorizationUri = authorizationUri;
            }

            public String getTokenUri() {
                return tokenUri;
            }

            public void setTokenUri(String tokenUri) {
                this.tokenUri = tokenUri;
            }

            public String getUserInfoUri() {
                return userInfoUri;
            }

            public void setUserInfoUri(String userInfoUri) {
                this.userInfoUri = userInfoUri;
            }
        }
    }
}
