package hello.itemservice.web.basic;


import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //생성자 + @Autowired 를 한 효과를 가짐.
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }
    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam Integer price,
            @RequestParam Integer quantity,
            Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.saveItem(item);

        model.addAttribute("item", item);
        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model){
        itemRepository.saveItem(item);

//        model.addAttribute("item", item); //@ModelAttribute("xxx") 를 쓰면 xxx이름으로 모델에 자동으로 추가해줌.
        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, Model model){
        itemRepository.saveItem(item);

//        model.addAttribute("item", item); //@ModelAttribute("xxx") 를 쓰면 xxx이름으로 모델에 자동으로 추가해줌.
        //@ModelAttribute에서 ()안에 변수가 없으면 Item->item / HelloData -> hellodata 처럼 Class 이름에서 소문자로 바꿔서 model에 저장
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV4( Item item, Model model){
        itemRepository.saveItem(item);
        //@ModelAttribute도 생략가능.
        return "basic/item";
    }
//    @PostMapping("/add")
    public String addItemV5( Item item, Model model){
        itemRepository.saveItem(item);
        //@ModelAttribute도 생략가능.
        return "redirect:/basic/items/"+item.getId();
    }
    @PostMapping("/add")
    public String addItemV6(Item item, Model model, RedirectAttributes redirectAttributes){
        Item saveItem = itemRepository.saveItem(item);
        //reredirectAttributes는 redirect시 url에 잘 저장되었다는 것을 알려주는 역할
        // (저장시 url:http://localhost:8080/basic/items/3?status=true)
        // + retuen시 {itemId}라고 사용가능(addAttribute했으니까)
        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);
        //@ModelAttribute도 생략가능.
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";

    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item){
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";

    }

    /**
     * 테스트용 아이템 추가
     */
    @PostConstruct
    public void init(){
        itemRepository.saveItem(new Item("itemA", 10000, 10));
        itemRepository.saveItem(new Item("itemB", 50000, 50));
    }
}
