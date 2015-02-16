package com.feisystems.icas.infrastructure;



public interface EmailServicesGateway {

    public void sendVerificationToken(EmailServiceTokenModel model);
}
