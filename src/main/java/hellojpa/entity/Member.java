package hellojpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Member.findByUsername", query = "select m from Member m where m.name = :name")
public class Member {

	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "USERNAME", nullable = false, length = 20)
	private String name;
	private int age;

	// @ManyToOne(fetch = FetchType.EAGER) // JOIN 해서 미리 가져옴
	@ManyToOne(fetch = FetchType.LAZY) // Member 를 조회하면 Member 만 조회하고 Team 은 조회하지 않음 (fake class), 실제 team 을 사용하는 시점에 DB 조회를 통한 객체 초기화
	@JoinColumn(name = "TEAM_ID")
	private Team team;

//	@Column(name = "TEAM_ID")
//	private Long teamId;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

}
