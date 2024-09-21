package com.gmenegatto.wallet_api.domain.authorization;

public record AuthorizationServiceResponse(String status, AuthorizationServiceData data) {

    public boolean isAuthorized() {
        return data.authorization();
    }
}
