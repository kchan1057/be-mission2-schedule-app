package com.example.bemission2scheduleapp.lv1_2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWriterRequestDto {
    private String writer;
    private String password;
}
