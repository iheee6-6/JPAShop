package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //기본적으로 트랜잭션 안에서 돌아가야함.
//@AllArgsConstructor //생성자 만들어줌
@RequiredArgsConstructor //final 필드만 가지고 생성자를 만들어줌
public class MemberService {

   // @Autowired //spring이 빈에 있는 것을 인젝션
    private final MemberRepository memberRepository;

    //@Autowired //생략해두 됨
   // public MemberService(MemberRepository memberRepository) {
    //    this.memberRepository = memberRepository;
    //}

    //회원가입
    @Transactional //따로 써주면 우선권 먹음 readonly false
    public Long join(Member member){
        validateDuplicateMember(member); //중복회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        //exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }


    //회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
