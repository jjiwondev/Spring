package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() { //JPA스펙상 기본 생성자를 설정해야함.
    }

    public Address(String city, String street, String zipcode) { //변경불가능하게 setter없이 생성자만 사용
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
