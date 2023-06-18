package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member memberA = creatMember("지원");

        Book book = createBook("1Q84", "하루키", 10, 10000);

        int orderCount = 2;


        Long orderId = orderService.order(memberA.getId(), book.getId(), orderCount);
        //when
        Order getOrder = orderRepository.findOne(orderId);

        //then

        assertThat(getOrder.getStatus()).as("주문시 상태는 order")
                .isEqualTo(OrderStatus.ORDER);
        assertThat(getOrder.getOrderItems().size()).as("주문한 상품 종류 검사")
                .isEqualTo(1);
        assertThat(getOrder.getTotalPrice()).as("주문가격은 수량*가격")
                .isEqualTo(orderCount * 10000);
        assertThat(book.getStockQuantity()).as("주문수량만큼 재고가 줄어야.")
                .isEqualTo(8);

    }

    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //given
        Member memberA = creatMember("지원");
        Book book = createBook("1Q84", "하루키", 10, 10000);
        //when
        int orderCount = 111;
        //then
//        orderService.order(memberA.getId(), book.getId(), orderCount);
        Assertions.assertThrows(NotEnoughStockException.class,
                ()->orderService.order(memberA.getId(), book.getId(), orderCount));
    }
    @Test
    public void 주문취소() throws Exception{
        //given
        Member member = creatMember("지원");
        Book book = createBook("1Q84", "하루키", 10, 10000);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        Order getOrder = orderRepository.findOne(orderId);

        //when
        getOrder.cancel();

        //then
        assertThat(getOrder.getStatus()).as("order의 Status가 CANCEL로 바뀌어야함.")
                .isEqualTo(OrderStatus.CANCEL);
        assertThat(book.getStockQuantity()).as("취소된 item은 재고가 증가해야한다.")
                .isEqualTo(10);
    }

    private Book createBook(String name, String author, int stockQuantity, int price) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

    private Member creatMember(String memberName) {
        Member memberA = new Member();
        memberA.setName(memberName);
        memberA.setAddress(new Address("서울", "돌곶이로", "123-123"));
        em.persist(memberA);
        return memberA;
    }

}