package vl.example.accountsservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vl.example.accountscommon.dto.enums.Status;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_coin", schema = "accounts")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "c_code")
    private String code;

    @Column(name = "c_name")
    private String name;

    @Column(name = "c_price")
    private Float price;

    @Column(name = "c_change1h")
    private Float change1h;

    @Column(name = "c_change24h")
    private Float change24h;

    @Column(name = "c_change7d")
    private Float change7d;

    @Column(name = "c_created_at")
    private LocalDateTime createdAt;

    @Column(name = "c_updated_at")
    private LocalDateTime updatedAt;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "c_status")
    private Status status;

}
