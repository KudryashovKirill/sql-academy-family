package com.example.sql_academy_airport.repository;

import com.example.sql_academy_airport.model.Good;
import com.example.sql_academy_airport.model.GoodType;
import com.example.sql_academy_airport.util.exception.WrongIdForUpdateException;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class GoodRepositoryImpl implements GoodRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    @Autowired
    public GoodRepositoryImpl(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template)
                .withTableName("goods")
                .usingGeneratedKeyColumns("good_id");
    }

    @Override
    public Good create(Good good) {
        Map<String, Object> params = Map.of(
                "good_name", good.getGoodName(),
                "type", good.getType().getGoodTypeId()
        );
        Number id = insert.executeAndReturnKey(params);
        good.setGoodId(id.longValue());
        return good;
    }

    @Override
    public Good getById(Long id) {
        String sqlQuery = """
                SELECT g.good_id,
                       g.good_name,
                       t.good_type_id,
                       t.good_type_name 
                FROM goods g
                JOIN good_types t ON g.type = t.good_type_id
                WHERE g.good_id = ?
                """;
        try {
            return template.queryForObject(sqlQuery, (rs, rowNum) ->
                    rowMapper(rs), id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("no good found for id");
        }
    }

    @Override
    public Good update(Good good, Long id) {
        String sqlQuery = """
                UPDATE goods
                SET good_name = ?
                WHERE good_id = ?
                """;
        int countOfUpdated = template.update(sqlQuery, good.getGoodName(), id);
        if (countOfUpdated == 0) {
            throw new WrongIdForUpdateException("can`t update good by id");
        }
        good.setGoodId(id);
        return good;
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        String sqlQuery = """
                DELETE FROM goods
                WHERE good_id = ?
                """;
        int countOfDeleted = template.update(sqlQuery, id);
        return Map.of("deleted", countOfDeleted > 0);
    }

    public Map<String, Integer> getMostExpensiveGood(String goodTypeName, Integer limit) {
        String sqlQuery = """
                SELECT good_name, unit_price
                FROM goods
                JOIN payments ON goods.good_id = payments.good
                JOIN good_types ON goods.type = good_types.good_type_id
                WHERE good_type_name = ? AND unit_price = (
                    SELECT MAX(unit_price)
                    FROM payments
                    JOIN goods ON Payments.good = goods.good_id
                    JOIN good_types ON goods.type = good_types.good_type_id
                    WHERE good_type_name = ?
                )
                LIMIT ?
                """;
        return template.queryForObject(sqlQuery, (rs, rowNum) -> Map.of(rs.getString("good_name"),
                rs.getInt("unit_price")), goodTypeName, goodTypeName, limit);
    }

    private Good rowMapper(ResultSet rs) throws SQLException {
        return new Good(
                rs.getLong("good_id"),
                rs.getString("good_name"),
                new GoodType(
                        rs.getLong("good_type_id"),
                        rs.getString("good_type_name")
                )
        );
    }
}
