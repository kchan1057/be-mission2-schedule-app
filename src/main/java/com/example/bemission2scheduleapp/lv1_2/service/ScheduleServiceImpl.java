package com.example.bemission2scheduleapp.lv1_2.service;

import com.example.bemission2scheduleapp.lv1_2.dto.ScheduleRequestDto;
import com.example.bemission2scheduleapp.lv1_2.dto.ScheduleResponseDto;
import com.example.bemission2scheduleapp.lv1_2.entitiy.Schedule;
import com.example.bemission2scheduleapp.lv1_2.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto saveTodo(ScheduleRequestDto dto) {
        Schedule schedule = new Schedule(
                dto.getTodo(),
                dto.getWriter(),
                dto.getPassword(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        return scheduleRepository.saveTodo(schedule);
    }

    @Override
    public List<ScheduleResponseDto> findAllSchedule(String writer, LocalDateTime modifiedAt) {
        return scheduleRepository.findAllSchedule(writer, modifiedAt);
    }


    @Override
    public ScheduleResponseDto findById(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateTodo(Long id, String todo, String password) {
        if(todo == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The todo are required value.");
        }

        int updatedRow = scheduleRepository.updateTodo(id, todo, password);

        if(updatedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Transactional
    @Override
    public ScheduleResponseDto updateWriter(Long id, String password, String writer) {
        if(writer == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The writer are required value.");
        }

        int updatedRow = scheduleRepository.updateWriter(id, writer, password);

        if(updatedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Schedule schedule = scheduleRepository.findByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    @Override
    public void deleteTodo(Long id, String password) {
        int deletedRow = scheduleRepository.deleteTodo(id, password);

        if(deletedRow == 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
