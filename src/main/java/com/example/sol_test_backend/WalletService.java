// src/main/java/com/example/soltestbackend/WalletService.java
package com.example.sol_test_backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service // 이 클래스는 비즈니스 로직을 처리하는 서비스 계층의 컴포넌트임을 나타냅니다.
@RequiredArgsConstructor // final로 선언된 필드들을 위한 생성자를 자동으로 만들어줍니다 (의존성 주입).
public class WalletService {

    // final 키워드를 붙여, 이 서비스가 반드시 두 Repository를 필요로 함을 명시합니다.
    private final WalletRepository walletRepository;
    private final LockupRepository lockupRepository;

    // 지갑 연결 처리 로직
    @Transactional // 이 메소드 안의 모든 DB 작업은 하나의 트랜잭션으로 묶입니다. (하나라도 실패하면 모두 롤백)
    public void processWalletConnection(String walletAddress) {
        // Repository를 사용해 DB에서 지갑 정보를 찾아봅니다.
        walletRepository.findByWalletAddress(walletAddress)
                .ifPresentOrElse(
                        // Case 1: 지갑이 이미 존재할 경우 실행되는 람다식
                        wallet -> {
                            System.out.println("기존 지갑 [" + walletAddress + "] 접속.");
                        },
                        // Case 2: 지갑이 존재하지 않을 경우 실행되는 람다식
                        () -> {
                            System.out.println("신규 지갑 [" + walletAddress + "] 접속. 임시 락업 데이터를 생성합니다.");

                            // 1. 새로운 Wallet 객체를 만들고 DB에 저장합니다.
                            Wallet newWallet = new Wallet();
                            newWallet.setWalletAddress(walletAddress);
                            walletRepository.save(newWallet);

                            // 2. 12개월치 임시 락업 데이터를 생성합니다.
                            List<Lockup> lockups = new ArrayList<>();
                            for (int i = 1; i <= 12; i++) {
                                Lockup lockup = new Lockup();
                                lockup.setWallet(newWallet);
                                lockup.setUnlockMonth(String.format("2025-%02d", i));
                                lockup.setAmount(new BigDecimal(10000 * i));
                                lockups.add(lockup);
                            }
                            // 3. 생성된 락업 데이터 리스트를 DB에 한 번에 저장합니다.
                            lockupRepository.saveAll(lockups);
                        }
                );
    }

    // 락업 정보 조회 로직
    @Transactional(readOnly = true) // 이 트랜잭션은 데이터를 변경하지 않고 읽기만 하므로, 성능 최적화 옵션을 켭니다.
    public List<Lockup> getLockupsByWalletAddress(String walletAddress) {
        return lockupRepository.findByWallet_WalletAddressOrderByUnlockMonthAsc(walletAddress);
    }
}