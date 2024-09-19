package dev.ehyeon.insert.post.repository;

import dev.ehyeon.insert.post.dto.InsertPostDto;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    public void insert(String title, String content) {
        String sql = "INSERT INTO post (title, content, created_date, last_modified_date) VALUES (?, ?, ?, ?)";

        LocalDateTime now = LocalDateTime.now();

        jdbcTemplate.update(sql, title, content, now, now);
    }

    public void batchInsert(List<InsertPostDto> insertPostDtos) {
        String sql = "INSERT INTO post (title, content, created_date, last_modified_date) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                InsertPostDto insertPostDto = insertPostDtos.get(i);

                LocalDateTime now = LocalDateTime.now();

                ps.setString(1, insertPostDto.title());
                ps.setString(2, insertPostDto.content());
                ps.setObject(3, now);
                ps.setObject(4, now);
            }

            @Override
            public int getBatchSize() {
                return insertPostDtos.size();
            }
        });
    }

    public long count() {
        String sql = "SELECT COUNT(*) FROM post";

        Long count = jdbcTemplate.queryForObject(sql, Long.class);

        return count == null ? 0 : count;
    }
}
