package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository1 { // 엔티티 찾아주는 애

    @PersistenceContext
    private EntityManager em; //boot가 엔티티매니저 생성함..

    public Long save(Member1 member){
        em.persist(member);
        return member.getId();
    }

    public Member1 find(Long id){
        return em.find(Member1.class,id);
    }
}
