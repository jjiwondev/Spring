package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        log.info("MemberController_createForm");
        model.addAttribute("memberForm", new MemberForm());
        //엔티티를 폼으로 쓰지 않는 이유는 엔티티를 최대한 순수하게 유지하기 위함.(dependency없이 하도록 하기 위해)
        //따라서 MemberForm이라는 DTO를 사용하는 것.
        //memberList뿌릴때도 DTO를 사용하는게 좋으나 이번예제에는 그냥 진행함.
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        log.info("MemberController_create");
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String members(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        //memberList뿌릴때도 DTO를 사용하는게 좋으나 이번예제에는 그냥 진행함.
        //주의:엔티티는 절대 API로 호출하면 안됨!
        return "members/memberList";
    }
}
