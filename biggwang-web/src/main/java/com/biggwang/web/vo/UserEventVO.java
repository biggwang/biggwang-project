package com.biggwang.web.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEventVO {

    private String timestamp;
    private String userAgent;
    private String colorName;
    private String userName;

}
