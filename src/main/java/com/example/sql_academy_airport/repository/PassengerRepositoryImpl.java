package com.example.sql_academy_airport.repository;

import com.example.sql_academy_airport.model.Passenger;
import com.example.sql_academy_airport.util.exception.WrongIdForUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class PassengerRepositoryImpl implements PassengerRepository {
    private JdbcTemplate template;
    private SimpleJdbcInsert insert;

    @Autowired
    public PassengerRepositoryImpl(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template)
                .withTableName("passengers")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Passenger create(Passenger passenger) {
        Map<String, Object> params = Map.of(
                "name", passenger.getName()
        );
        Number id = insert.executeAndReturnKey(params);
        passenger.setId(id.longValue());
        return passenger;
    }

    @Override
    public Passenger getById(Long id) {
        String sqlQuery = """
                SELECT *
                FROM passengers
                WHERE id = ?
                """;
        try {
            return template.queryForObject(sqlQuery, (rs, rowNum) ->
                    rowMapper(rs), id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("Passenger not found with id");
        }
    }

    @Override
    public Passenger update(Passenger passenger, Long id) {
        String sqlQuery = """
                UPDATE passengers
                SET name = ?
                WHERE id = ?
                """;
        int countOfUpdatedPassengers = template.update(sqlQuery, passenger.getName(), id);
        if (countOfUpdatedPassengers == 0) {
            throw new WrongIdForUpdateException("wrong id for update passenger");
        }
        passenger.setId(id);
        return passenger;
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        String sqlQuery = """
                DELETE FROM passengers
                WHERE id = ?
                """;
        int countOfDeletedPassengers = template.update(sqlQuery, id);
        return Map.of("deleted", countOfDeletedPassengers > 0);
    }

    public List<String> getAllNames() {
        String sqlQuery = "SELECT name FROM passengers";
        return template.query(sqlQuery, (rs, rowNum) -> rs.getString("name"));
    }

    public List<String> getAllEndWith(String namePostfix) {
        String sqlQuery = "SELECT name FROM passengers WHERE name LIKE '%?'";
        return template.query(sqlQuery, (rs, rowNum) -> rs.getString("name"));
    }

    public List<String> getAllLongestNames() {
        String sqlQuery = "SELECT name FROM passengers WHERE LENGTH(name) = (SELECT MAX(LENGTH(name)) FROM passengers)";
        return template.query(sqlQuery, (rs, rowNum) -> rs.getString("name"));
    }

    public Map<Long, Integer> getCountPassengerByTrip() {
        String sqlQuery = "SELECT t.id as trip_id, COUNT(pit.passenger) as count FROM trips t LEFT JOIN pass_in_trip pit ON t.id " +
                "= pit.trip GROUP BY t.id";
        return template.query(sqlQuery, rs -> {
            Map<Long, Integer> map = new HashMap<>();
            while (rs.next()) {
                map.put(rs.getLong("trip_id"), rs.getInt("count"));
            }
            return map;
        });
    }

    private Passenger rowMapper(ResultSet rs) throws SQLException {
        return new Passenger(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
}
