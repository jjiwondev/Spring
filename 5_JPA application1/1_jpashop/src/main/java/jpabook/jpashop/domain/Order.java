package jpabook.jpashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") //관례상 이름이 Order가 되어버리기 떄문에 orders로 변경해줌.
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //지정된 생성자가 아닌 다른 생성자를 불러오는 것을 막는것.
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    //cascade를 이렇게 넣으면 order를 persist했을때 OrderItems에 들어와있는 oderItem에 대해서도 persist를 남겨줌 -> 즉, orderService에서 save할때  따로 레포지토리에 저장을 안해줌.
    //order가 oderItem과 delivery를 관리하기 때문에 이렇게 넣은 것.(orderItem과 delivery는 order만 참조하기 때문)
    //다른엔티티에서도 delivery나 orderItem을 가져다 쓰는 경우 cascade.ALL 옵션을 사용하면 안됨.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderTime;

    @Enumerated(EnumType.STRING) //ORDINAL로는 절대 하지말것 -> String을 0,1등으로 바꾸어서 넣는데, 한개가 더 추가되면 오류난다.
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드 ==// //핵심적으로 컨트롤 하는 애가 가지고 있는 것이 좋음. //양방향일때 쓰면 좋음.
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderTime(LocalDateTime.now());
        return order;
    }

    //==비지니스 로직 ==//
    /**
     * 주문 취소
     */
    public void cancel(){
        if (this.delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능 합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
    }
    //==조회 로직==//

    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : this.orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}
