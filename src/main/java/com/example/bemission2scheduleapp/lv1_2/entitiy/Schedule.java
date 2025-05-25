package com.example.bemission2scheduleapp.lv1_2.entitiy;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {

    @Setter
    private Long id;
    private String todo;
    private String writer;
    private String password;
    @Setter
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime modifiedAt;

    public Schedule(String todo, String writer, String password, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.todo = todo;
        this.writer = writer;
        this.password = password;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
