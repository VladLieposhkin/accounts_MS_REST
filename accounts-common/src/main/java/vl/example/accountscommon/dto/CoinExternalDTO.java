package vl.example.accountscommon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vl.example.accountscommon.dto.enums.Status;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoinExternalDTO {
    private String id;
    private String symbol;
    private String name;
    private String nameid;
    private Integer rank;
    private Float price_usd;
    private Float percent_change_24h;
    private Float percent_change_1h;
    private Float percent_change_7d;
    private String market_cap_usd;
    private Float volume24;
    private Float volume24a;
    private String csupply;
    private String price_btc;
    private String tsupply;
    private String msupply;

    public CoinDTO toCoinDTO() {
        return CoinDTO.builder()
                .code(this.getId())
                .name(this.getName())
                .price((float) (Math.round(this.getPrice_usd() * 100) / 100.00))
                .change1h(this.getPercent_change_1h())
                .change24h(this.getPercent_change_24h())
                .change7d(this.getPercent_change_7d())
                .updatedAt(LocalDateTime.now())
                .status(Status.ACTIVE)
        .build();
    }
}


