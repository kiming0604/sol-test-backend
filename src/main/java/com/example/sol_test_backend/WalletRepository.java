// src/main/java/com/example/soltestbackend/WalletRepository.java
package com.example.sol_test_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// JpaRepository<관리할 Entity, Entity의 ID 타입>를 상속받습니다.
// 이것만으로도 기본적인 저장(save), 조회(findById), 삭제(delete) 기능이 자동으로 구현됩니다.
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    // Spring Data JPA의 메소드 쿼리 기능:
    // 'findByWalletAddress' 라는 이름만으로 'walletAddress' 필드를 기준으로
    // Wallet을 찾아주는 SQL 쿼리를 자동으로 생성하고 실행해줍니다.
    // Optional<T>는 결과가 null일 수도 있음을 나타내는 안전한 방법입니다.
    Optional<Wallet> findByWalletAddress(String walletAddress);
}