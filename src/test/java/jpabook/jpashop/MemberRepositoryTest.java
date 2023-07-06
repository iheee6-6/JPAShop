package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository1 memberRepository;


    @Test
    @Transactional
   // @Rollback(false) //commit 하기
    public void testMember() throws Exception{
        //given
        Member1 member= new Member1();
        member.setUsername("memberA");

        //when
        Long saveId=memberRepository.save(member);
        Member1 findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); //true 영속성으로 일차 캐시가져옴
        System.out.println("=="+(findMember==member));
     }
}