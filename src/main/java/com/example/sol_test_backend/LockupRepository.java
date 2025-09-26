// src/main/java/com/example/soltestbackend/LockupRepository.java
package com.example.sol_test_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LockupRepository extends JpaRepository<Lockup, Integer> {

    // Wallet Entity 내부의 walletAddress 필드를 기준으로 Lockup 리스트를 조회하고,
    // unlockMonth 필드를 기준으로 오름차순 정렬해주는 메소드입니다.
    List<Lockup> findByWallet_WalletAddressOrderByUnlockMonthAsc(String walletAddress);
}