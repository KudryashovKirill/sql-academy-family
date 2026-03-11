package com.example.sql_academy_airport.repository;

import com.example.sql_academy_airport.model.Trip;
import com.example.sql_academy_airport.util.exception.WrongIdForUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class TripRepositoryImpl implements TripRepository {
    private JdbcTemplate template;
    private SimpleJdbcInsert insert;
    private final CompanyRepository companyRepository;

    @Autowired
    public TripRepositoryImpl(JdbcTemplate template, CompanyRepository companyRepository) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template)
                .withTableName("trips")
                .usingGeneratedKeyColumns("id");
        this.companyRepository = companyRepository;
    }

    @Override
    public Trip create(Trip trip) {
        Map<String, Object> params = Map.of("plane", trip.getPlane(),
                "company", trip.getCompany().getId(),
                "town_from", trip.getTownFrom(),
                "town_to", trip.getTownTo(),
                "time_out", trip.getTimeOut(),
                "time_in", trip.getTimeIn());
        Number id = insert.executeAndReturnKey(params);
        trip.setId(id.longValue());
        return trip;
    }

    @Override
    public Trip getById(Long id) {
        String sqlQuery = """
                SELECT *
                FROM trips
                WHERE id = ?
                """;
        try {
            return template.queryForObject(sqlQuery, (rs, rowNum) -> {
                        Trip t = rowMapper(rs);
                        t.setCompany(companyRepository.getById(rs.getLong("company")));
                        return t;
                    }, id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("trip not found with id");
        }
    }

    @Override
    public Trip update(Trip trip, Long id) {
        String sqlQuery = """
                UPDATE trips
                SET plane = ?, town_from = ?, town_to = ?, time_out = ?, time_in = ?
                WHERE id = ?
                """;
        int countOfUpdated = template.update(sqlQuery, trip.getPlane(), trip.getTownFrom(), trip.getTownTo(),
                trip.getTimeOut(), trip.getTimeIn(), id);
        if (countOfUpdated == 0) {
            throw new WrongIdForUpdateException("wrong id for update trip");
        }
        trip.setId(id);
        return trip;
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        String sqlQuery = """
                DELETE FROM trips
                WHERE id = ?
                """;
        int countOfDeletedTrips = template.update(sqlQuery, id);
        return Map.of("deleted", countOfDeletedTrips > 0);
    }

    public List<Trip> getAllFromTownFrom(String townFrom) {
        String sqlQuery = "SELECT * FROM trips WHERE town_from = ?";
        return template.query(sqlQuery, (rs, rowNum) -> rowMapper(rs), townFrom);
    }

    public Integer getAllPlanesByName(String name) {
        String sqlQuery = "SELECT COUNT(*) FROM trips WHERE plane = ?";
        return template.queryForObject(sqlQuery, Integer.class, name);
    }

    public List<String> getAllPlanesByTownTo(String townTo) {
        String sqlQuery = "SELECT DISTINCT plane FROM trips WHERE town_to = ?";
        return template.query(sqlQuery, (rs, rowNum) -> rs.getString("plane"), townTo);
    }

    public Map<String, Duration> getAllFromTownFromMap(String townFrom) {
        String sqlQuery = """
                SELECT town_to, time_out, time_in
                FROM trips
                WHERE town_from = ?
                """;

        return template.query(sqlQuery, rs -> {
            Map<String, Duration> result = new HashMap<>();

            while (rs.next()) {
                String townTo = rs.getString("town_to");

                LocalDateTime timeOut = rs.getTimestamp("time_out").toLocalDateTime();
                LocalDateTime timeIn = rs.getTimestamp("time_in").toLocalDateTime();

                Duration flightTime = Duration.between(timeOut, timeIn);

                result.put(townTo, flightTime);
            }

            return result;
        }, townFrom);
    }

    public List<Trip> getAllBetweenTime(LocalDate timeStart, LocalDate timeEnd) {
        String sqlQuery = "SELECT * FROM trips WHERE time_out BETWEEN ? AND ?";
        return template.query(sqlQuery, (rs, rowNum) -> rowMapper(rs), timeStart, timeEnd);
    }

    private Trip rowMapper(ResultSet rs) throws SQLException {
        return new Trip(rs.getLong("id"),
                rs.getString("plane"),
                rs.getString("town_from"),
                rs.getString("town_to"),
                rs.getTimestamp("time_out").toLocalDateTime(),
                rs.getTimestamp("time_in").toLocalDateTime()
        );
    }
}
