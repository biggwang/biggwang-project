package com.biggwang.web.hellojpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void select() {
        memberRepository.findAll();
    }

    @Test
    public void save() {
        memberRepository.save(
                MemberEntity.builder()
                        .memberId(2L)
                        .memberName("홍길동")
                        .build()
        );
    }

}