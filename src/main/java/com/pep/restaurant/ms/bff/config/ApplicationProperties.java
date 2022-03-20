package com.pep.restaurant.ms.bff.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private final Here here = new Here();

    private final Keycloak keycloak = new Keycloak();

    private final MsManager msManager = new MsManager();

    public Keycloak getKeycloak(){
        return keycloak;
    }

    public MsManager getMsManager(){ return msManager; }

    public Here getHere(){ return here; }

    public static class Keycloak{

        private String serverUrl;

        private String realm;

        private String username;

        private String password;

        private String clientId;

        private String clientSecret;

        /**
         * Get keycloak Server Url.
         * @return keycloak server url.
         */
        public String getServerUrl() {
            return serverUrl;
        }

        /**
         * Set keycloak Server Url.
         * @param serverUrl Server url.
         */
        public void setServerUrl(final String serverUrl) {
            this.serverUrl = serverUrl;
        }

        /**
         * Get Keycloak realm.
         * @return realm.
         */
        public String getRealm() {
            return realm;
        }

        /**
         * Set keycloak realm.
         * @param realm realm.
         */
        public void setRealm(final String realm) {
            this.realm = realm;
        }

        /**
         * Get Keycloak Username username.
         * @return username.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Set keycloak username.
         * @param username username.
         */
        public void setUsername(final String username) {
            this.username = username;
        }

        /**
         * Get keycloak password.
         * @return password.
         */
        public String getPassword() {
            return password;
        }

        /**
         * Set keycloak password.
         * @param password password.
         */
        public void setPassword(final String password) {
            this.password = password;
        }

        /**
         * Get keycloak client id.
         * @return clientId.
         */
        public String getClientId() {
            return clientId;
        }

        /**
         * Set keycloak client id.
         * @param clientId client id.
         */
        public void setClientId(final String clientId) {
            this.clientId = clientId;
        }

        /**
         * Get keycloak client secret.
         * @return client Secret.
         */
        public String getClientSecret() {
            return clientSecret;
        }

        /**
         * Set keycloak client secret.
         * @param clientSecret client secret.
         */
        public void setClientSecret(final String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }

    public static class MsManager{

        private String url = "http://localhost:8082/pep/restaurant/manager";

        /**
         * Get Ms Manager Url.
         * @return Ms Manager Url.
         */
        public String getUrl() {
            return url;
        }

        /**
         * Set Ms Manager Url.
         * @param url Ms Manager Url.
         */
        public void setUrl(final String url) {
            this.url = url;
        }

    }

    public static class Here{

        private String accessKeyId;

        private String accessKeySecret;

        private String tokenEndpointUrl;

        private String herePlacesApiUrl = "https://places.ls.hereapi.com/places/v1/discover/here/";

        /**
         * Get Here Access Key Id.
         * @return Here Access Key Id.
         */
        public String getAccessKeyId() {
            return accessKeyId;
        }

        /**
         * Set Here Access Key Id.
         * @param accessKeyId Here Access Key Id.
         */
        public void setAccessKeyId(final String accessKeyId) {
            this.accessKeyId = accessKeyId;
        }

        /**
         * Get Here Access Key Secret.
         * @return Here Access Key Secret.
         */
        public String getAccessKeySecret() {
            return accessKeySecret;
        }

        /**
         * Set Here Access Key Secret.
         * @param accessKeySecret Here Access Key Secret.
         */
        public void setAccessKeySecret(final String accessKeySecret) {
            this.accessKeySecret = accessKeySecret;
        }

        /**
         * Get Here Token Endpoint Url.
         * @return Here Token Endpoint Url.
         */
        public String getTokenEndpointUrl() {
            return tokenEndpointUrl;
        }

        /**
         * Set Here Token Endpoint Url.
         * @param tokenEndpointUrl Here Token Endpoint Url.
         */
        public void setTokenEndpointUrl(final String tokenEndpointUrl) {
            this.tokenEndpointUrl = tokenEndpointUrl;
        }

        /**
         * Get Here Places Api Url.
         * @return Here Places Api Url.
         */
        public String getHerePlacesApiUrl() {
            return herePlacesApiUrl;
        }

        /**
         * Set Here Places Api Url.
         * @param herePlacesApiUrl Here Places Api Url.
         */
        public void setHerePlacesApiUrl(final String herePlacesApiUrl) {
            this.herePlacesApiUrl = herePlacesApiUrl;
        }


    }


}
