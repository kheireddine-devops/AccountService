package com.erp.ecommerce.common.actions.query;

import java.util.UUID;

public interface AccountQuery {
    record GetAllCustomerAccountsQuery() {}
    record GetAllAccountsQuery() {}
    record GetAccountByIdQuery(UUID accountId) {}
}
