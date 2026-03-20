package org.example.treciparcijalnitest.repository;

import lombok.AllArgsConstructor;
import org.example.treciparcijalnitest.domain.ProgramObrazovanja;
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
public class JdbcProgramObrazovanjaRepository implements ProgramObrazovanjaRepository{
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ProgramObrazovanja> getAll(){
        return jdbcTemplate.query("SELECT * FROM ProgramObrazovanja", new JdbcProgramObrazovanjaRepository.ProgramObrazovanjaMapper());
    }

    @Override
    public Optional<ProgramObrazovanja> getById(Integer id) {
        String sql = "SELECT * FROM ProgramObrazovanja WHERE ProgramObrazovanjaID = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new JdbcProgramObrazovanjaRepository.ProgramObrazovanjaMapper(), id));
    }


    @Override
    public ProgramObrazovanja save(ProgramObrazovanja programObrazovanja) {
        final String SQL = "INSERT INTO ProgramObrazovanja (Naziv, CSVET) OUTPUT INSERTED.ProgramObrazovanjaID VALUES (?, ?)";
        Integer generatedId = jdbcTemplate.queryForObject(SQL, Integer.class, programObrazovanja.getName(), programObrazovanja.getCSVET());
        programObrazovanja.setId(generatedId);
        return programObrazovanja;
    }



    @Override
    public Optional<ProgramObrazovanja> update(ProgramObrazovanja programObrazovanja, Integer id) {
        if(exists(id)) {
            final String SQL = "UPDATE ProgramObrazovanja SET Naziv = ?, CSVET = ? WHERE ProgramObrazovanjaID = ?";
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL);
                ps.setString(1, programObrazovanja.getName());
                ps.setInt(2, programObrazovanja.getCSVET());
                ps.setInt(3, id);
                return ps;
            });
            programObrazovanja.setId(id);
            return Optional.of(programObrazovanja);
        }
        else {
            return Optional.empty();
        }
    }


    @Override
    public boolean delete(Integer id) {
        if(exists(id)) {
            jdbcTemplate.update("DELETE FROM ProgramObrazovanja WHERE ProgramObrazovanjaID = ?", id);
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public boolean exists(Integer id) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT (*) FROM ProgramObrazovanja WHERE ProgramObrazovanjaID = ?", Integer.class, id);
        return count > 0;
    }


    private static class ProgramObrazovanjaMapper implements RowMapper<ProgramObrazovanja> {

        public ProgramObrazovanja mapRow(ResultSet rs, int i) throws SQLException {

            ProgramObrazovanja programObrazovanja = new ProgramObrazovanja();
            programObrazovanja.setId(rs.getInt("ProgramObrazovanjaID"));
            programObrazovanja.setName(rs.getString("Naziv"));
            programObrazovanja.setCSVET(rs.getInt("CSVET"));

            return programObrazovanja;
        }
    }
}
