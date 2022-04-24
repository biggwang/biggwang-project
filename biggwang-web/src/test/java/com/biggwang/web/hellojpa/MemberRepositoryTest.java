package com.biggwang.web.hellojpa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void select() {
        memberRepository.findAll();
    }

    @Test
    @Rollback(value = false)
    public void save() {
        Team team = Team.builder().teamName("개나리반").build();
        Member member1 = Member.builder().memberName("류아린").build();
        Member member2 = Member.builder().memberName("류아랑").build();
        member1.setTeam(team);
        member2.setTeam(team);
        entityManager.persist(team);
        entityManager.persist(member1);
        entityManager.persist(member2);
    }
}