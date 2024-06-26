package vl.example.accountsexaminer.internal;

import vl.example.accountscommon.dto.AccountDTO;

import java.util.List;

public interface InternalMailServiceRestClient {
    Integer sendMail(List<AccountDTO> accountDTOs);
}
