package com.example.bemission2scheduleapp.lv1_2.controller;


import com.example.bemission2scheduleapp.lv1_2.dto.ScheduleRequestDto;
import com.example.bemission2scheduleapp.lv1_2.dto.ScheduleResponseDto;
import com.example.bemission2scheduleapp.lv1_2.dto.UpdateWriterRequestDto;
import com.example.bemission2scheduleapp.lv1_2.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/todos")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    //----------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> addSchedule(@RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.saveTodo(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules(
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime modifiedAt
    ) {
        return new ResponseEntity<>(scheduleService.findAllSchedule(writer, modifiedAt), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.findById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}/todo")
    public ResponseEntity<ScheduleResponseDto> updateTodo(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateTodo(id, dto.getTodo(), dto.getPassword()), HttpStatus.OK);
    }

    @PatchMapping("/{id}/writer")
    public ResponseEntity<ScheduleResponseDto> updateWriter(
            @PathVariable Long id,
            @RequestBody UpdateWriterRequestDto dto) {
        return new ResponseEntity<>(scheduleService.updateWriter(id, dto.getPassword(), dto.getWriter()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto dto) {
        scheduleService.deleteTodo(id, dto.getPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
