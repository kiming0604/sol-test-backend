// src/main/java/com/example/soltestbackend/WalletController.java
package com.example.sol_test_backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController // 이 클래스는 RESTful API 요청을 처리하는 컨트롤러임을 나타냅니다.
@RequestMapping("/api") // 이 컨트롤러의 모든 API 주소는 "/api"로 시작합니다. (예: /api/wallet/connect)
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://main.d164bkvjxp617w.amplifyapp.com"})
public class WalletController {

    private final WalletService walletService;

    // API 1: 지갑 연결 (POST /api/wallet/connect)
    @PostMapping("/wallet/connect")
    public ResponseEntity<?> connectWallet(@RequestBody Map<String, String> payload) {
        String walletAddress = payload.get("walletAddress");
        if (walletAddress == null || walletAddress.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "지갑 주소가 필요합니다."));
        }
        walletService.processWalletConnection(walletAddress);
        return ResponseEntity.ok(Map.of("message", "지갑 정보가 성공적으로 처리되었습니다."));
    }

    // API 2: 락업 정보 조회 (GET /api/lockups/{walletAddress})
    @GetMapping("/lockups/{walletAddress}")
    public ResponseEntity<List<Lockup>> getLockups(@PathVariable String walletAddress) {
        List<Lockup> lockups = walletService.getLockupsByWalletAddress(walletAddress);
        if (lockups.isEmpty()) {
            // 조회 결과가 없으면 404 Not Found 응답을 보냅니다.
            return ResponseEntity.notFound().build();
        }
        // 조회 결과가 있으면 200 OK 응답과 함께 데이터를 보냅니다.
        return ResponseEntity.ok(lockups);
    }
}