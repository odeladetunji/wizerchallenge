package com.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BookDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Long CategoryId;

}
