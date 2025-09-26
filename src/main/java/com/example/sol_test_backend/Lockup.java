// src/main/java/com/example/soltestbackend/Lockup.java
package com.example.sol_test_backend;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal; // 정확한 소수점 계산을 위해 BigDecimal 사용

@Entity
@Table(name = "lockups")
@Getter @Setter
public class Lockup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // 다대일 관계: 여러 개의 락업(Lockup) 정보가 하나의 지갑(Wallet)에 속합니다.
    @JoinColumn(name = "wallet_id", nullable = false) // 'wallet_id' 컬럼을 통해 Wallet 테이블과 연결됩니다.
    private Wallet wallet;

    @Column(name = "unlock_month", length = 10, nullable = false)
    private String unlockMonth;

    @Column(nullable = false, precision = 20, scale = 9) // 총 20자리, 소수점 9자리까지 저장 가능
    private BigDecimal amount;

    @Column(name = "is_unlocked")
    private boolean isUnlocked = false; // 기본값은 false
}