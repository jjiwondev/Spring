package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("M") //singleTable이므로 구분하기 위한 구분자
@Getter @Setter
public class Movie extends Item{
    private String director;
    private String actor;
}
