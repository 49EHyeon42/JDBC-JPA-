package dev.ehyeon.insert;

import dev.ehyeon.insert.post.repository.PostJdbcDao;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PostJdbcDao.class)
public class PostJdbcDaoInsertTest {

    final NumberFormat numberformat = NumberFormat.getNumberInstance(Locale.US);

    @Autowired
    PostJdbcDao postJdbcDao;

    @Test
    @Transactional
    public void jdbcInsertTestWith1KRecords() {
        insertTestWithSizeRecords(1_000);
    }

    @Test
    @Transactional
    public void jdbcInsertTestWith10KRecords() {
        insertTestWithSizeRecords(10_000);
    }

    @Test
    @Transactional
    public void jdbcInsertTestWith100KRecords() {
        insertTestWithSizeRecords(100_000);
    }

    @Test
    @Transactional
    public void jdbcInsertTestWith1MRecords() {
        insertTestWithSizeRecords(1_000_000);
    }

    private void insertTestWithSizeRecords(int size) {
        Instant startTime = Instant.now();

        for (int i = 0; i < size; i++) {
            postJdbcDao.insert("title", "content");
        }

        Instant endTime = Instant.now();

        System.out.println("JDBC insert of " + numberformat.format(size) + " records took: " + Duration.between(startTime, endTime).toMillis() + "ms");

        long count = postJdbcDao.count();

        Assertions.assertEquals(size, count);
    }
}
