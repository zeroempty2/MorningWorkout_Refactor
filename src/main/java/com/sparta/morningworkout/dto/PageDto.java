package com.sparta.morningworkout.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {

    int page;

    int size;

    String sortBy;

    boolean isAsc;

}
