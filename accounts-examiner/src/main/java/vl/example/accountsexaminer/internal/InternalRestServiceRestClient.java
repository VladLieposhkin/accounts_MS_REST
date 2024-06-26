package vl.example.accountsexaminer.internal;

import vl.example.accountscommon.dto.AccountDTO;
import vl.example.accountscommon.dto.CoinDTO;

import java.util.List;

public interface InternalRestServiceRestClient {
    List<CoinDTO> getCoinsData();
    Integer setCoinsData(List<CoinDTO> coinDTOs);
    List<AccountDTO> getAccountsData(Integer threshold);
}
