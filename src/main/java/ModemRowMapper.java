import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModemRowMapper implements RowMapper<Modem> {
    boolean isRealDevice;

    public ModemRowMapper(boolean isRealDevice) {
        this.isRealDevice = isRealDevice;
    }

    @Override
    public Modem mapRow(ResultSet rs, int rowNum) throws SQLException {
        Modem modem = new Modem(
                rs.getString("name"),
                rs.getString("ip"),
                isRealDevice);

        modem.setId(rs.getString("id"));
        modem.setPort(rs.getInt("port"));
        modem.setDescription(rs.getString("description"));
        modem.setLocation(rs.getString("location"));

        return modem;
    }
}
