package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.MemberType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {

	public static void main(String[] args) {
		// EntityManagerFactory : 하나만 생성해서 application 전체에서 공유
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		// EntityManager : thread 간 공유를 하면 안된다. (사용하고 버려야 함)
		EntityManager em = emf.createEntityManager();
		// JPA 모든 data 변경은 transaction 안에서 실행
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Member member = new Member();
			// member.setId(100L);
			member.setName("Jay");
			member.setAge(29);
			member.setMemberType(MemberType.USER);


			em.persist(member);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}

}
