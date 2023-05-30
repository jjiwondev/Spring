package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ControllerV2 {

    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    //controllerV1과 비슷하나 얘는 MyView를 객체로 반환. 즉, 서블릿에게 MyView를 넘겨주어 서블릿이 jsp에게 forward를 해주는 기능을 구현하기 위함.
}
