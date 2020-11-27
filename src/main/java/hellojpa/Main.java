package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.Team;
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

			Team team = new Team();
			team.setName("teamA");
			em.persist(team);

			Member member = new Member();
			member.setName("Jay");
			member.setAge(29);
			member.setTeamId(team.getId());
			em.persist(member);

			// 객체를 테이블에 맞추어 데이터 중심으로 모델링하면, 협력 관계를 만들 수 없다.
			Member findMember = em.find(Member.class, member.getId());
			Long teamId = findMember.getTeamId();
			Team findTeam = em.find(Team.class, teamId);
			System.out.println(findMember.getName());
			System.out.println(findTeam.getName());

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}

}
