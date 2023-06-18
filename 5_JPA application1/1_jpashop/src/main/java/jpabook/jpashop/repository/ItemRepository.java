package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item); //item은 JPA에 저장하기 전까지 id값이 없으므로 id가 없는 경우 persistfmf gowna.
        }else{
            em.merge(item); //여기서의 save는 update라고 생각하면 됨. 실무에서 거의 안쓴다.
            //merge는 준영속 상태의 엔티티를 영속성 엔티티로 변경하는 것.
            //주의:변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만,병합을 사용하면 모든 속성이 변경된다.
            // 병합시 값이 없으면 null 로 업데이트 할 위험도 있다. (병합은 모든 필드를 교체한다.)
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
