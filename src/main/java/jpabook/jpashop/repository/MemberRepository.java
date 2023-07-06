package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor // 변경 불가능한 안전한 객체 생성 가능
public class MemberRepository {
    //@PersistenceContext //springboot는 autowired로 이 역할을 할 수 있게 해줌
    //@Autowired
    private final EntityManager em;

   // public MemberRepository(EntityManager em) { //변경 불가능한 안전한 객체 생성 가능
    //    this.em = em;
    //}

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        // "select m from Member m where m.name = :name
        // select m  from member m where m.name= :name
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
