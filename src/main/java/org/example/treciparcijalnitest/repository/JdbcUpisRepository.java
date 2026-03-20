package org.example.treciparcijalnitest.repository;

import lombok.AllArgsConstructor;
import org.example.treciparcijalnitest.domain.Upis;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
@AllArgsConstructor
public class JdbcUpisRepository implements UpisRepository{
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Upis> getAll(){
        return jdbcTemplate.query("SELECT * FROM Upis", new JdbcUpisRepository.UpisMapper());
    }

    @Override
    public Optional<Upis> getById(Integer id) {
        String sql = "SELECT * FROM Upis WHERE UpisID = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new JdbcUpisRepository.UpisMapper(), id));
    }


    @Override
    public Upis save(Upis upis) {
        final String SQL = "INSERT INTO Upis (IDProgramObrazovanja, IDPolaznik) OUTPUT INSERTED.UpisID VALUES (?, ?)";
        Integer generatedId = jdbcTemplate.queryForObject(SQL, Integer.class, upis.getProgramId(), upis.getStudentId());
        upis.setId(generatedId);
        return upis;
    }



    @Override
    public Optional<Upis> update(Upis upis, Integer id) {
        if(exists(id)) {
            final String SQL = "UPDATE Upis SET IDProgramObrazovanja = ?, IDPolaznik = ? WHERE UpisID = ?";
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL);
                ps.setInt(1, upis.getProgramId());
                ps.setInt(2, upis.getStudentId());
                ps.setInt(3, id);
                return ps;
            });
            upis.setId(id);
            return Optional.of(upis);
        }
        else {
            return Optional.empty();
        }
    }


    @Override
    public boolean delete(Integer id) {
        if(exists(id)) {
            jdbcTemplate.update("DELETE FROM Upis WHERE UpisID = ?", id);
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public boolean exists(Integer id) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT (*) FROM Upis WHERE UpisID = ?", Integer.class, id);
        return count > 0;
    }


    private static class UpisMapper implements RowMapper<Upis> {

        public Upis mapRow(ResultSet rs, int i) throws SQLException {

            Upis upis = new Upis();
            upis.setId(rs.getInt("UpisID"));
            upis.setProgramId(rs.getInt("IDProgramObrazovanja"));
            upis.setStudentId(rs.getInt("IDPolaznik"));

            return upis;
        }
    }
}
