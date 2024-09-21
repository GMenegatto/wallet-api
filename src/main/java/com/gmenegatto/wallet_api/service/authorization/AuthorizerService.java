package com.gmenegatto.wallet_api.service.authorization;

import com.gmenegatto.wallet_api.domain.authorization.AuthorizationServiceResponse;
import com.gmenegatto.wallet_api.domain.wallet.Transaction;
import com.gmenegatto.wallet_api.exception.UnauthorizedTransactionException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Service
public class AuthorizerService {

    private final RestClient restClient = RestClient.create();


    public void authorize() {
        var response = restClient.get()
                .uri("https://util.devi.tools/api/v2/authorize")
                .retrieve()
                .toEntity(AuthorizationServiceResponse.class);

        if (response.getStatusCode().isError() || Objects.isNull(response.getBody()) || (!response.getBody().isAuthorized())) {
            throw new UnauthorizedTransactionException("Transaction unauthorized");
        }
    }
}
