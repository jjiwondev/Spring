package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void saveTest(){
        //given
        Item itemA = new Item("itemA", 10000, 100);
        //when
        Item saveItem = itemRepository.saveItem(itemA);

        //then
        Item findItem = itemRepository.findById(saveItem.getId());
        assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll(){
        //given
        Item itemA = new Item("itemA", 10000, 100);
        Item itemB = new Item("itemB", 50000, 500);
        itemRepository.saveItem(itemA);
        itemRepository.saveItem(itemB);
        //when
        List<Item> itemList = itemRepository.findAll();

        //then
        assertThat(itemList.size()).isEqualTo(2);
        assertThat(itemList).contains(itemA, itemB);
    }

    @Test
    void updateItem(){
        //given
        Item itemA = new Item("itemA", 10000, 100);
        itemRepository.saveItem(itemA);
        //when
        Item updateItem = new Item("updateItem", 50000, 500);
        itemRepository.update(itemA.getId(),updateItem);

        Item findItem = itemRepository.findById(itemA.getId());
        //then
        assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateItem.getQuantity());

    }


}