package com.example.sql_academy_airport.repository;

import com.example.sql_academy_airport.dto.output.SpendsOnFun;
import com.example.sql_academy_airport.model.FamilyMember;
import com.example.sql_academy_airport.util.exception.WrongIdForUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Repository
public class FamilyMemberRepositoryImpl implements FamilyMemberRepository {
    JdbcTemplate template;
    SimpleJdbcInsert insert;

    @Autowired
    public FamilyMemberRepositoryImpl(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template)
                .withTableName("family_members")
                .usingGeneratedKeyColumns("member_id");
    }

    @Override
    public FamilyMember create(FamilyMember familyMember) {
        Map<String, Object> params = Map.of("member_name", familyMember.getMemberName(),
                "status", familyMember.getStatus(),
                "birthday", familyMember.getBirthday());
        Number id = insert.executeAndReturnKey(params);
        familyMember.setMemberId(id.longValue());
        return familyMember;
    }

    @Override
    public FamilyMember getById(Long id) {
        String sqlQuery = """
                SELECT *
                FROM family_members
                WHERE member_id = ?
                """;
        try {
            return template.queryForObject(sqlQuery,
                    (rs, rowNum) -> rowMapper(rs),
                    id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("Family member not found with id");
        }
    }

    @Override
    public FamilyMember update(FamilyMember familyMember, Long id) {
        String sqlQuery = """
                UPDATE family_members
                SET member_name = ?, status = ?, birthday = ?
                WHERE member_id = ?
                """;
        int updatedCompanies = template.update(sqlQuery, familyMember.getMemberName(), familyMember.getStatus(),
                familyMember.getBirthday(), id);
        if (updatedCompanies == 0) {
            throw new WrongIdForUpdateException("wrong id for update member");
        }
        familyMember.setMemberId(id);
        return familyMember;
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        String sqlQuery = """
                DELETE FROM family_members
                WHERE member_id = ?
                """;
        int countOfDeletedCompanies = template.update(sqlQuery, id);
        return Map.of("deleted", countOfDeletedCompanies > 0);
    }

    public Set<String> getOldestMember() {
        String sqlQuery = """
                SELECT member_name
                FROM family_members
                WHERE birthday = (
                            SELECT MIN(birthday)
                            FROM family_members
                )
                """;
        return new HashSet<>(template.query(sqlQuery, (rs, rowNum) ->
                rs.getString("member_name")));
    }

    public Set<String> getStatusByGoodType(String goodType) {
        String sqlQuery = """
                SELECT status
                FROM family_members fm
                         JOIN payments ps ON fm.member_id = ps.family_member
                         JOIN goods gs ON ps.good = gs.good_id
                WHERE good_name = ?
                GROUP BY status;
                """;
        return new HashSet<>(template.query(sqlQuery, (rs, rowNum) -> rs.getString("status"),
                goodType));
    }

    public List<SpendsOnFun> getSpendsOnFun() {
        String sqlQuery = """
                SELECT status, member_name, SUM(unit_price * amount) AS costs
                FROM family_members fm
                JOIN payments p ON p.family_member = fm.member_id
                JOIN goods g ON p.good = g.good_id
                JOIN good_types gt ON g.type = gt.good_type_id
                WHERE good_type_name = 'Electronics'
                GROUP BY fm.status, member_name
                """;
        return template.query(sqlQuery, (rs, rowNum) -> rowMapperSpends(rs));
    }

    public Set<String> getProductsOneMoreTime() {
        String sqlQuery = """
                SELECT good_name
                FROM goods g
                JOIN payments p ON g.good_id = p.good
                GROUP BY good_name
                HAVING COUNT(g.good_name) > 1
                """;
        return new HashSet<>(template.query(sqlQuery, (rs, rowNum) ->
                rs.getString("good_name")));
    }

    public Set<String> getAllByStatus(String status) {
        String sqlQuery = "SELECT member_name FROM family_members WHERE status = ?";
        return new HashSet<>(template.query(sqlQuery, (rs, rowNum) ->
                rs.getString("member_name"), status));
    }

    public Map<String, Integer> getSpendsByDate(LocalDate timeStart, LocalDate timeEnd) {
        String sqlQuery = """
                SELECT member_name, SUM(amount * unit_price) AS costs
                FROM family_members
                JOIN payments ON member_id = family_member
                WHERE date >= ? AND date < ?
                GROUP BY member_name
                """;
        return template.queryForObject(sqlQuery, (rs, rowNum) ->
                Map.of(rs.getString("member_name"), rs.getInt("costs")), timeStart, timeEnd);
    }

    private FamilyMember rowMapper(ResultSet rs) throws SQLException {
        return new FamilyMember(
                rs.getLong("member_id"),
                rs.getString("status"),
                rs.getString("member_name"),
                rs.getDate("birthday").toLocalDate());
    }

    private SpendsOnFun rowMapperSpends(ResultSet rs) throws SQLException {
        return new SpendsOnFun(
                rs.getString("status"),
                rs.getString("member_name"),
                rs.getInt("costs")
        );
    }
}
