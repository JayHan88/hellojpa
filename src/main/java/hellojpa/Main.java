package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.Team;
import java.util.List;
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
			// 양 쪽 객체에 모두 값을 넣어라
			member.setTeam(team);
			em.persist(member);
			team.getMembers().add(member);

//			em.flush(); // Dirty Checking (변경 감지) - 변경 사항이 있으면 update query 를 추가적으로 날린다. 쓰기 지연 SQL 저장소에 있는 query 를 DB 에 반영한다.
//			em.clear(); // 영속성 conetext 에 있는 1차 cache 를 모두 지운다.

			// 기본 JPQL - List 객체 하나마다 쿼리가 한 번 나가서 비추함
//			String jpql = "select m From Member m where m.name = 'Jay'";
//			List<Member> result = em.createQuery(jpql, Member.class).getResultList();
//			for (Member x : result) {
//				System.out.println("username = " + x.getName() + ", " + "teamname = " + member.getTeam().getName());
//			}

			// fetch join 방법
			String jpql2 = "select m from Member m join fetch m.team";
			List<Member> result2 = em.createQuery(jpql2, Member.class).setFirstResult(0).setMaxResults(20).getResultList();
			for (Member y : result2) {
				System.out.println("username = " + y.getName() + ", " + "teamname = " + member.getTeam().getName());
			}

			tx.commit();

//			//Name Query 이용
//			List<Member> result3 = em.createNamedQuery("Member.findByUsername", Member.class).setParameter("name", "Jay").getResultList();
//			for (Member z : result3) {
//				System.out.println("username = " + z.getName() + ", " + "teamname = " + member.getTeam().getName());
//			}

			// Member -> Team
//			Member findMember = em.find(Member.class, member.getId());
//			em.detach(findMember);
//			em.clear();
//			findMember.setName("T아카데미");

//			Team findTeam = findMember.getTeam();
//			findTeam.getName();

//			List<Member> members = findTeam.getMembers();
//			for (Member member1 : members) {
//				System.out.println("member1 = " + member1.toString());
//			}

			// Team -> Member
//			Team findTeam2 = em.find(Team.class, team.getId());
//			int MemberSize = findTeam2.getMembers().size();
//			System.out.println(MemberSize);
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();

		}
		emf.close();
	}

}
