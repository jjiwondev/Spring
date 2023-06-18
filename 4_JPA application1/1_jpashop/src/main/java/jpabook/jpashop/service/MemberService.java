package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //JPA의 데이터 변경이나 로직들은 transaction 안에서 생성되어야.
//readOnly= true로 해놓으면 읽기 성능을 최적화 함. 따라서, set을 해주는 join에는 따로 @Transactional을 달아줘야함. 여기서는 대부분 get에 가까워서 일단 여기에 transactional넣어줬음.
@RequiredArgsConstructor //final들어간 애들에 대해서 생성자를 자동으로 만들어 주고 생성자에 @Autowired를 달아줌.
public class MemberService {

    private final MemberRepository memberRepository;

    // join // validateDuplicateMember //findMembers //findOne

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        System.out.println("findMembers = " + findMembers);
        for (Member findMember : findMembers) {
            System.out.println("findMember.getName() = " + findMember.getName());
        }
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
