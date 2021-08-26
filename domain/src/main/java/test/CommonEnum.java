package test;

import lombok.Getter;

@Getter
public enum CommonEnum {

    OKAY("좋았어!");

    private String meesage;

    CommonEnum(String meesage) {
        this.meesage = meesage;
    }
}
