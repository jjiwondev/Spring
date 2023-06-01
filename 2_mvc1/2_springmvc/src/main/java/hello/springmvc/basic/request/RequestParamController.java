package hello.springmvc.basic.request;


import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge){
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";  //@ResponseBody를 사용하면 응답바디에 ok를 바로 넣어줌(RestController와 같은 효과)
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age){ //@RequestParam의 para를 생략할 수 있는데 대신 파라미터이름과 변수 이름이 동일해야함.
        log.info("username={}, age={}", username, age);
        return "ok";  //@ResponseBody를 사용하면 응답바디에 ok를 바로 넣어줌(RestController와 같은 효과)
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age){
        //String, int, Integer등 단순 타입이면 @RequestParam도 생략가능 (주관: 좀 너무 과한거 아닌가?라는 생각)
        log.info("username={}, age={}", username, age);
        return "ok";  //@ResponseBody를 사용하면 응답바디에 ok를 바로 넣어줌(RestController와 같은 효과)
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username, //username이 없으면 오류가 남
            @RequestParam(required = false) Integer age){ //Integer로 바꾼이유: 값을 안넣으면 null이 들어가는데 int에 null이 들어가면 오류나서.
        //주의: null과 ""는 다르다. 즉, username= 이렇게만 넣으면 통과가 됨.
        log.info("username={}, age={}", username, age);
        return "ok";
    }
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(defaultValue = "guest") String username,
            @RequestParam(defaultValue = "-1") int age){
        //주의: null과 ""는 다르다. 즉, username= 이렇게만 넣으면 통과가 됨.
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(
            @RequestParam Map<String, Object> paramMap){//map으로도 받을 수 있음.
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1brute")
    public String modelAttributeV1brute( //바꿀예정(V1과차이점보기)
            @RequestParam String username,
            @RequestParam int age){
        HelloData helloData = new HelloData();
        helloData.setUsername(username);
        helloData.setAge(age);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
        //modelAttirubte가 있으면 hellodat객체를 생성하고 요청파라미터 이름으로 객체의 프로퍼티를 찾는다.
        //프로퍼티? -> 객체에 getUsername(), setUsername()메서드가 있으면 이 객체는 username이라는 프로퍼티를 가지고 있음.
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2( HelloData helloData){//@ModelAttribute도 생략 가능
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

}
