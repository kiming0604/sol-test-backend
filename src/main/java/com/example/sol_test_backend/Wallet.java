// src/main/java/com/example/soltestbackend/Wallet.java
package com.example.sol_test_backend;

import jakarta.persistence.*; // JPA 어노테이션을 사용하기 위해 import
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity // 이 클래스는 데이터베이스 테이블과 매핑되는 클래스임을 나타냅니다.
@Table(name = "wallets") // 실제 DB의 'wallets' 테이블과 연결됩니다.
@Getter @Setter // Lombok: getId(), setId(), getWalletAddress() 같은 메소드를 자동으로 만들어줍니다.
public class Wallet {

    @Id // 이 필드가 테이블의 기본 키(Primary Key)임을 나타냅니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 값을 DB가 자동으로 생성(AUTO_INCREMENT)하도록 합니다.
    private Integer id;

    @Column(name = "wallet_address", length = 44, nullable = false, unique = true) // DB 컬럼의 상세 속성을 지정합니다.
    private String walletAddress;

    @Column(name = "created_at", updatable = false) // 한 번 생성되면 수정되지 않도록 설정합니다.
    private LocalDateTime createdAt = LocalDateTime.now(); // 객체가 생성될 때 현재 시간이 자동으로 저장됩니다.
}