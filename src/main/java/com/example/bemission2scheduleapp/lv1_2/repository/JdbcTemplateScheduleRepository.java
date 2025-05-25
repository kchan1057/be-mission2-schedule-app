package com.example.bemission2scheduleapp.lv1_2.repository;

import com.example.bemission2scheduleapp.lv1_2.dto.ScheduleResponseDto;
import com.example.bemission2scheduleapp.lv1_2.entitiy.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveTodo(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("writer", schedule.getWriter());
        parameters.put("password", schedule.getPassword());
        parameters.put("created_at", Timestamp.valueOf(schedule.getCreatedAt()));
        parameters.put("modified_at", Timestamp.valueOf(schedule.getModifiedAt()));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        schedule.setId(key.longValue()); //@Setter 넣는거 잊지말기!
        return new ScheduleResponseDto(schedule);
    }


    private RowMapper<ScheduleResponseDto> scheduleRowMapper(){
        return new RowMapper<ScheduleResponseDto>(){
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                    rs.getLong("id"),
                    rs.getString("todo"),
                    rs.getString("writer"),
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("modified_at").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2(){
        return new RowMapper<Schedule>(){
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                Schedule schedule = new Schedule(
                        rs.getString("todo"),
                        rs.getString("writer"),
                        rs.getString("password"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("modified_at").toLocalDateTime()
                );
                schedule.setId(rs.getLong("id"));
                return schedule;
            }
        };
    }


    @Override
    public List<ScheduleResponseDto> findAllSchedule(String writer, LocalDateTime modifiedAt) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        Object[] params;

        if (writer != null && modifiedAt != null) {
            sql.append(" AND writer = ? AND modified_at >= ?");
            params = new Object[]{writer, Timestamp.valueOf(modifiedAt)};
        } else if (writer != null) {
            sql.append(" AND writer = ?");
            params = new Object[]{writer};
        } else if (modifiedAt != null) {
            sql.append(" AND modified_at >= ?");
            params = new Object[]{Timestamp.valueOf(modifiedAt)};
        } else {
            params = new Object[]{};
        }

        return jdbcTemplate.query(sql.toString(), scheduleRowMapper(), params);
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override
    public int updateTodo(Long id, String todo, String password) {
        Schedule schedule = findByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }
        return jdbcTemplate.update("UPDATE schedule SET todo = ?, modified_at = NOW() WHERE id = ?", todo, id);
    }

    @Override
    public int updateWriter(Long id, String writer, String password) {
        Schedule schedule = findByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        return jdbcTemplate.update("UPDATE schedule SET writer = ?, modified_at = NOW() WHERE id = ?", writer, id);
    }


    @Override
    public int deleteTodo(Long id, String password) {
        Schedule schedule = findByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
        }

        return jdbcTemplate.update("DELETE FROM schedule WHERE id = ?", id);
    }


    @Override
    public Schedule findByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedule where id = ?", scheduleRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }
}
