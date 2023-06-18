package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		Hello hello = new Hello();
		hello.setDate("hello");
		String date = hello.getDate();
		System.out.println("date = " + date);
		SpringApplication.run(JpashopApplication.class, args);
	}

}
