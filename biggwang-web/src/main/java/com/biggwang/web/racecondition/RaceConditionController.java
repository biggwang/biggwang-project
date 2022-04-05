package com.biggwang.web.racecondition;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RaceConditionController {

    private static Integer counter = 0;
    private static final int MAX_GOODS = 8;

    @GetMapping("/init")
    public String init() {
        counter = 0;
        return "상품 갯수가 초기화 되었습니다";
    }

    @GetMapping("/order")
    public String order(int idx) {
        if (counter >= MAX_GOODS) {
            return "품절!";
        }
        counter++;
        return "성공 " + counter;
    }

    @GetMapping("/sync/order")
    public String syncOrder(int idx) {
        synchronized (counter) {
            if (counter >= MAX_GOODS) {
                return "품절!";
            }
            return "성공 " + (++counter);
        }
    }

    @GetMapping("/count")
    public Integer count() {
        return counter;
    }
}
