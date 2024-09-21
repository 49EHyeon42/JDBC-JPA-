package dev.ehyeon.insert;

import dev.ehyeon.insert.post.dto.InsertPostDto;
import dev.ehyeon.insert.post.repository.PostJdbcDao;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
public class PostJdbcDaoBatchInsertTest {

    final NumberFormat numberformat = NumberFormat.getNumberInstance(Locale.US);

    @Autowired
    PostJdbcDao postJdbcDao;

    @Test
    @Transactional
    public void batchInsertTestWith1KRecords() {
        batchInsertTestWithSizeRecords(1_000);
    }

    @Test
    @Transactional
    public void batchInsertTestWith10KRecords() {
        batchInsertTestWithSizeRecords(10_000);
    }

    @Test
    @Transactional
    public void batchInsertTestWith100KRecords() {
        batchInsertTestWithSizeRecords(100_000);
    }

    @Test
    @Transactional
    public void batchInsertTestWith1MRecords() {
        batchInsertTestWithSizeRecords(1_000_000);
    }

    private void batchInsertTestWithSizeRecords(int size) {
        final int BATCH_SIZE = 1_000;

        Instant startTime = Instant.now();

        List<InsertPostDto> insertPostDtos = new ArrayList<>(BATCH_SIZE);

        for (int i = 0; i < size; i++) {
            insertPostDtos.add(new InsertPostDto("title", "content"));

            if (insertPostDtos.size() == BATCH_SIZE) {
                postJdbcDao.batchInsert(insertPostDtos);
                insertPostDtos.clear();
            }
        }

        if (!insertPostDtos.isEmpty()) {
            postJdbcDao.batchInsert(insertPostDtos);
        }

        Instant endTime = Instant.now();

        System.out.println("JDBC batch insert of " + numberformat.format(size) + " records took: " + Duration.between(startTime, endTime).toMillis() + "ms");

        long count = postJdbcDao.count();

        Assertions.assertEquals(size, count);
    }

    @Test
    @Transactional
    public void batchInsertTestWith1MRecordsAnd10BatchSize() {
        batchInsertTestWithSizeRecordsAndBatchSize(1_000_000, 10);
    }

    @Test
    @Transactional
    public void batchInsertTestWith1MRecordsAnd100BatchSize() {
        batchInsertTestWithSizeRecordsAndBatchSize(1_000_000, 100);
    }

    @Test
    @Transactional
    public void batchInsertTestWith1MRecordsAnd1KBatchSize() {
        batchInsertTestWithSizeRecordsAndBatchSize(1_000_000, 1_000);
    }

    @Test
    @Transactional
    public void batchInsertTestWith1MRecordsAnd10KBatchSize() {
        batchInsertTestWithSizeRecordsAndBatchSize(1_000_000, 10_000);
    }

    @Test
    @Transactional
    public void batchInsertTestWith1MRecordsAnd100KBatchSize() {
        batchInsertTestWithSizeRecordsAndBatchSize(1_000_000, 100_000);
    }

    private void batchInsertTestWithSizeRecordsAndBatchSize(int size, int batchSize) {
        Instant startTime = Instant.now();

        List<InsertPostDto> insertPostDtos = new ArrayList<>(batchSize);

        for (int i = 0; i < size; i++) {
            insertPostDtos.add(new InsertPostDto("title", "content"));

            if (insertPostDtos.size() == batchSize) {
                postJdbcDao.batchInsert(insertPostDtos);
                insertPostDtos.clear();
            }
        }

        if (!insertPostDtos.isEmpty()) {
            postJdbcDao.batchInsert(insertPostDtos);
        }

        Instant endTime = Instant.now();

        System.out.println("JDBC batch insert of " + numberformat.format(size) + " records took: " + Duration.between(startTime, endTime).toMillis() + "ms");

        long count = postJdbcDao.count();

        Assertions.assertEquals(size, count);
    }
}
