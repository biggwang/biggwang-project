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
@DiscriminatorValue("친구송금")
public class Movie extends Item {

    private String author;

    private String isbn;
}
