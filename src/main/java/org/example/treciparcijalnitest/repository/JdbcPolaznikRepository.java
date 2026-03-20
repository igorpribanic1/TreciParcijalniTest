package org.example.treciparcijalnitest.repository;

import lombok.AllArgsConstructor;
import org.example.treciparcijalnitest.domain.Polaznik;
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
public class JdbcPolaznikRepository implements PolaznikRepository {
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Polaznik> getAll(){
        return jdbcTemplate.query("SELECT * FROM Polaznik", new PolaznikMapper());
    }

    @Override
    public Optional<Polaznik> getById(Integer id) {
        String sql = "SELECT * FROM Polaznik WHERE PolaznikID = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, new PolaznikMapper(), id));
    }


    @Override
    public Polaznik save(Polaznik polaznik) {
        final String SQL = "INSERT INTO Polaznik (Ime, Prezime) OUTPUT INSERTED.PolaznikID VALUES (?, ?)";
        Integer generatedId = jdbcTemplate.queryForObject(SQL, Integer.class, polaznik.getFirstName(), polaznik.getLastName());
        polaznik.setId(generatedId);
        return polaznik;
    }



    @Override
    public Optional<Polaznik> update(Polaznik polaznik, Integer id) {
        if(exists(id)) {
            final String SQL = "UPDATE Polaznik SET Ime = ?, Prezime = ? WHERE PolaznikID = ?";
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL);
                ps.setString(1, polaznik.getFirstName());
                ps.setString(2, polaznik.getLastName());
                ps.setInt(3, id);
                return ps;
            });
            polaznik.setId(id);
            return Optional.of(polaznik);
        }
        else {
            return Optional.empty();
        }
    }


    @Override
    public boolean delete(Integer id) {
        if(exists(id)) {
            jdbcTemplate.update("DELETE FROM Polaznik WHERE PolaznikID = ?", id);
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    public boolean exists(Integer id) {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT (*) FROM Polaznik WHERE PolaznikID = ?", Integer.class, id);
        return count > 0;
    }


    private static class PolaznikMapper implements RowMapper<Polaznik> {

        public Polaznik mapRow(ResultSet rs, int i) throws SQLException {

            Polaznik polaznik = new Polaznik();
            polaznik.setId(rs.getInt("PolaznikID"));
            polaznik.setFirstName(rs.getString("Ime"));
            polaznik.setLastName(rs.getString("Prezime"));

            return polaznik;
        }
    }
}
