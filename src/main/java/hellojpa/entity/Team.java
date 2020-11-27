package hellojpa.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Team {

	@Id @GeneratedValue
	private Long id;

	private String name;

	@OneToMany(mappedBy = "team") // 난 주인이 아니야, team 이라는 것에 mappedby 됐을 뿐. 주인이 아니라서 읽기만 가능해.
	// 누구를 주인으로? FK가 있는 곳을 주인으로 정해라. 여기서는 Member.team 이 연관관계의 주인
	List<Member> members = new ArrayList<Member>();

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

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
}
