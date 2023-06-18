package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member") //연관관계에서 '주'가 아님.(읽기전용이 된다는 뜻)
    private List<Order> orders = new ArrayList<>();


}
