package com.aga.spring_jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Book {

    private String isbn;

    private String title;

//    private Author author;

    private Long authorId;
}
