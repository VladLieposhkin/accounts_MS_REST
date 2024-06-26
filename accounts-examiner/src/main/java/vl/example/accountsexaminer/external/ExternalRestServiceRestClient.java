package vl.example.accountsexaminer.external;

import vl.example.accountscommon.dto.CoinDTO;
import vl.example.accountscommon.dto.CoinExternalDTO;

import java.util.List;

public interface ExternalRestServiceRestClient {
    List<CoinExternalDTO> getExternalCoinsData(List<CoinDTO> coins);
}
