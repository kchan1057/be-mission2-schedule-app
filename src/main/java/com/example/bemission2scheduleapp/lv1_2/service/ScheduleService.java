package com.example.bemission2scheduleapp.lv1_2.service;

import com.example.bemission2scheduleapp.lv1_2.dto.ScheduleRequestDto;
import com.example.bemission2scheduleapp.lv1_2.dto.ScheduleResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleService {

    ScheduleResponseDto saveTodo(ScheduleRequestDto dto);
    List<ScheduleResponseDto> findAllSchedule(String writer, LocalDateTime modifiedAt);
    ScheduleResponseDto findById(Long id);
    ScheduleResponseDto updateTodo(Long id, String todo, String password);
    ScheduleResponseDto updateWriter(Long id, String writer, String password);
    void deleteTodo(Long id, String password);
}

