package com.example.sql_academy_airport.repository;

import com.example.sql_academy_airport.model.FamilyMember;
import com.example.sql_academy_airport.model.Good;
import com.example.sql_academy_airport.model.Payment;
import com.example.sql_academy_airport.util.exception.WrongIdForUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final JdbcTemplate template;
    private final SimpleJdbcInsert insert;

    @Autowired
    public PaymentRepositoryImpl(JdbcTemplate template) {
        this.template = template;
        this.insert = new SimpleJdbcInsert(template)
                .withTableName("payments")
                .usingGeneratedKeyColumns("payment_id");
    }

    @Override
    public Payment create(Payment payment) {
        Map<String, Object> params = Map.of(
                "amount", payment.getAmount(),
                "unit_price", payment.getUnitPrice(),
                "date", payment.getDate(),
                "family_member", payment.getFamilyMember().getMemberId(),
                "good", payment.getGood().getGoodId()
        );
        Number id = insert.executeAndReturnKey(params);
        payment.setPaymentId(id.longValue());
        return payment;
    }

    @Override
    public Payment getById(Long id) {
        String sqlQuery = """
                SELECT p.payment_id, 
                p.amount,
                p.unit_price,
                p.date,
                
                fm.member_id,
                fm.member_name,
                fm.status,
                fm.birthday,
                
                g.good_id,
                g.good_name
                
                FROM payments p 
                JOIN family_members fm ON fm.member_id = p.family_member
                JOIN goods g ON p.good = g.good_id
                WHERE payment_id = ?
                """;
        try {
            return template.queryForObject(sqlQuery, (rs, rowNum) -> mapFullPayment(rs), id);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("can`t get payment");
        }
    }

    @Override
    public Payment update(Payment payment, Long id) {
        String sqlQuery = """
                UPDATE payments
                SET amount = ?, unit_price = ?, date = ?, family_member = ?
                WHERE payment_id = ?
                """;
        int countOfUpdated = template.update(sqlQuery,
                payment.getAmount(),
                payment.getUnitPrice(),
                payment.getDate(),
                payment.getFamilyMember().getMemberId(),
                id
        );
        if (countOfUpdated == 0) {
            throw new WrongIdForUpdateException("");
        }
        payment.setPaymentId(id);
        return payment;
    }

    @Override
    public Map<String, Boolean> delete(Long id) {
        String sqlQuery = """
                DELETE FROM payments
                WHERE payment_id = ?
                """;
        int countOfDeleted = template.update(sqlQuery, id);
        return Map.of("deleted", countOfDeleted > 0);
    }

    @Override
    public List<Payment> findByFamilyMemberId(Long memberId) {
        String sqlQuery = """
                SELECT *
                FROM payments
                WHERE family_member = ?
                """;
        try {
            return template.query(sqlQuery, (rs, rowNum) ->
                    rowMapper(rs), memberId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchElementException("no payments found by member id");
        }
    }

    private Payment rowMapper(ResultSet rs) throws SQLException {
        return new Payment(
                rs.getLong("payment_id"),
                rs.getInt("amount"),
                rs.getInt("unit_price"),
                rs.getDate("date").toLocalDate()
        );
    }

    private Payment mapFullPayment(ResultSet rs) throws SQLException {
        Good good = new Good(
                rs.getLong("good_id"),
                rs.getString("good_name")
        );

        FamilyMember member = new FamilyMember(
                rs.getLong("member_id"),
                rs.getString("member_name"),
                rs.getString("status"),
                rs.getDate("birthday").toLocalDate()
        );

        return new Payment(
                rs.getLong("payment_id"),
                rs.getInt("amount"),
                rs.getInt("unit_price"),
                rs.getDate("date").toLocalDate(),
                member,
                good
        );
    }
}
