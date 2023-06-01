package hello.springmvc.basic;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController //@Controller를 사용하면 view를 반환하여야 하지만, RestController를 사용하면 String반환해도 됨.
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass()); //@Sl4fj로 대체 가능
    //패키지를 org.slf4j.Logger로 해야 함.

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        System.out.println("name = " + name);
        log.info(" info log={}", name);
        //출력은 INFO 37294 --- [nio-8080-exec-2] hello.springmvc.basic.LogTestController  :  info log=Spring
        //37294: 프로세스 아이디 / 실행한 쓰레드 이름 /  컨트롤러 이름                                 / 출력한 내용
        log.trace(" trace my log={}" + name); //이렇게 쓰면 +연산이 들어가서 리소스를 사용하게 됨. 그냥 위 처럼 쓰자.


        log.trace(" trace log = {}", name);
        log.debug(" debug log={}", name );
        log.warn(" warn log={}", name);
        log.error("error log={}", name);

        //trace와 debug는 일반 상태에서는 안보임. application properties에서 따로 세팅해줘야.
        //logging.level.hello.springmvc=trace 라고 하면 trace와 debug 까지 다 보임
        //logging.level.hello.springmvc=debug 라고하면 trace는 안보이고 debug만 보임.(나머지도 보임)
        //logging.level.hello.springmvc=info 라고 하면 기본적인애들(warn,error,info)만 보임.

        return "OK";
    }

}
