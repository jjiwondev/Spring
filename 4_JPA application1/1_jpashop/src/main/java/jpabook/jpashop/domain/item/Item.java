package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //Book, Movie, Album을 한 테이블에 때려박음.
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==// //이렇게 엔티티에 비지니스 로직을 집어넣는 것을 도메인 모델 패턴이라고 함.
    /**
     * stock증가
     */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    public void removeStock(int quantity) {
        int restQuantity = this.stockQuantity - quantity;
        if(restQuantity < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restQuantity;
    }
}
