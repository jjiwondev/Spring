package jpabook.jpashop.controller;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form) {

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String itemList(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("/items/{itemId}/edit")
    public String itemUpdateForm(@PathVariable("itemId") Long itemId, Model model) {
        Book item = (Book)itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setAuthor(item.getAuthor());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setIsbn(item.getIsbn());

        model.addAttribute("form", form);

        return "items/updateItemForm";
    }

    @PostMapping("/items/{itemId}/edit")
    public String itemUpdate(@PathVariable Long itemId, BookForm form) {
//        Book book = new Book();
//        book.setId(form.getId());
//        book.setName(form.getName());
//        book.setAuthor(form.getAuthor());
//        book.setPrice(form.getPrice());
//        book.setStockQuantity(form.getStockQuantity());
//        book.setIsbn(form.getIsbn());
//        itemService.saveItem(book);
        //주의: 위 처럼 코드를 짜게 된다면, new Book으로 새로운 객체를 불러온 것이므로 '준영속 엔티티'가 됨
        //준영속엔티티:영속성 컨테스트가 더이상 관리하지 않는 엔티티임. 영속성 엔티티의 경우 변경을 하게 되면 영속성 컨테스트가 변경을 자동으로 감지해서 JPA에 반영해주었음
        //그런데 위 처럼 짜게되면 준영속엔티티가 되므로  saveItem을 해주어야 함.
        // 따라서, updateItem처럼 기존의 영속성 컨테스트를 repository에서 꺼내고 그 후 변수를 세팅해주는 것이 좋음.
        //두번째 방법은 merge를 사용하는 방법이 있음(실무에서 사용 x) -> itemrepository에서 사용
        //또한, 컨트롤러에서 어설프게 엔티티를 만들어서 반환하는 것은 안좋음 -> updateItem을 써야하는 이유 두번째
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity(),form.getAuthor());
        return "redirect:/items";
    }
}
