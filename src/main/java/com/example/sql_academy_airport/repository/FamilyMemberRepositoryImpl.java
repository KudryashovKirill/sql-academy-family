package com.example.sql_academy_airport.repository;

import com.example.sql_academy_airport.model.FamilyMember;
import com.example.sql_academy_airport.util.exception.WrongIdForUpdateException;
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

    private FamilyMember rowMapper(ResultSet rs) throws SQLException {
        return new FamilyMember(
                rs.getLong("member_id"),
                rs.getString("status"),
                rs.getString("member_name"),
                rs.getDate("birthday").toLocalDate());
    }
}
