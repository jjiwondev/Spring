package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false) //spring에서의 transaction은 commit을 안하고 rollback하기 때문에 false설정 해주는 것
    //이거 안하면 Insert문이 안나감
    //이게 싫으면 entitiyManager를 Em으로 받아서 em.flush() 하면됨.
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("jiwon");

        //when
        Long savedId = memberService.join(member);

        //then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    }
    @Test
    public void 중복회원조회() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("jiwon");
        Member member2 = new Member();
        member2.setName("jiwon");
        //when
        memberService.join(member1);

        //then
        org.junit.jupiter.api.Assertions.assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
    }
}