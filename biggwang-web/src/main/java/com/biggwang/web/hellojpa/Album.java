package com.biggwang.web.hellojpa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("계좌이체")
public class Album extends Item {

    private String director;

    private String actor;
}
