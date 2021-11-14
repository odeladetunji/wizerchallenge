package com.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class FavouriteBooks {
    private ArrayList<Integer> bookIdList;
}
