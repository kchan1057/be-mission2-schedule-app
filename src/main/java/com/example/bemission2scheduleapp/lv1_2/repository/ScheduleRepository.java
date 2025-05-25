package com.example.bemission2scheduleapp.lv1_2.repository;

import com.example.bemission2scheduleapp.lv1_2.dto.ScheduleResponseDto;
import com.example.bemission2scheduleapp.lv1_2.entitiy.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {

    ScheduleResponseDto saveTodo(Schedule schedule);
    List<ScheduleResponseDto> findAllSchedule(String writer, LocalDateTime modifiedAt);
    Schedule findByIdOrElseThrow(Long id);
    Optional<Schedule> findById(Long id);
    int updateTodo(Long id, String todo, String password);
    int updateWriter(Long id, String writer, String password);
    int deleteTodo(Long id, String password);

}
