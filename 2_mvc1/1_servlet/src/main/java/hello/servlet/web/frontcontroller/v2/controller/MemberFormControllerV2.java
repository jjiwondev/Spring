package hello.servlet.web.frontcontroller.v2.controller;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV2 implements ControllerV2 {
//    String viewPath = "/WEB-INF/views/new-form.jsp";
//    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);//controller에서 view로 이동할때 사용하는 것.
//    dispatcher.forward(request,response); //이걸 해야 servlet에서 jsp를 호출할 수 있음.
    //위는 v1의 과정
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return new MyView("/WEB-INF/views/new-form.jsp"); //엄청 간략해졌음!
    }

}
