package com.biggwang.web.hellojpa;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Transactional
@SpringBootTest
@Rollback(value = false)
@ActiveProfiles(value = "local")
class MemberRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() {
        Team team = Team.builder().teamName("개나리반").build();
        Member member1 = Member.builder().memberName("류아린").build();
        Member member2 = Member.builder().memberName("류아랑").build();
        member1.setTeam(team);
        member2.setTeam(team);
        em.persist(team);
        em.persist(member1);
        em.persist(member2);
    }

    @Test
    @Rollback(value = false)
    public void find() {
         Member member = em.find(Member.class, 5L);
         Team team = member.getTeam();
         log.warn("### 회원 이름:{}", member.getMemberName());
         log.warn("### proxy");
//         log.warn("### 소속팀:{}", team.getTeamName());
    }
}