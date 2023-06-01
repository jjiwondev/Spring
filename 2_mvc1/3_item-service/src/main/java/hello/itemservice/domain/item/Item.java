package hello.itemservice.domain.item;


import lombok.Getter;
import lombok.Setter;

//@Data //핵심 도메인에는 @Data는 예측 불가능한 상황을 만들 수 있어 사용 지양
@Getter @Setter
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    //id제외하고 생성자 형성
    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
