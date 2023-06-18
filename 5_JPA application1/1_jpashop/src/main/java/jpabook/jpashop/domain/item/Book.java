package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") //singleTable이므로 구분하기 위한 구분자
@Getter @Setter
public class Book extends Item{
    private String author;
    private String isbn;
}
