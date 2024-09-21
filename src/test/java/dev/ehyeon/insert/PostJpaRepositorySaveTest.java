package dev.ehyeon.insert;

import dev.ehyeon.insert.post.entity.PostEntity;
import dev.ehyeon.insert.post.repository.PostJpaRepository;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostJpaRepositorySaveTest {

    final NumberFormat numberformat = NumberFormat.getNumberInstance(Locale.US);

    @Autowired
    PostJpaRepository postJpaRepository;

    @Test
    @Transactional
    public void jpaSaveTestWith1KRecords() {
        saveTestWithSizeRecords(1_000);
    }

    @Test
    @Transactional
    public void jpaSaveTestWith10KRecords() {
        saveTestWithSizeRecords(10_000);
    }

    @Test
    @Transactional
    public void jpaSaveTestWith100KRecords() {
        saveTestWithSizeRecords(100_000);
    }

    @Test
    @Transactional
    public void jpaSaveTestWith1MRecords() {
        saveTestWithSizeRecords(1_000_000);
    }

    private void saveTestWithSizeRecords(int size) {
        Instant startTime = Instant.now();

        for (int i = 0; i < size; i++) {
            postJpaRepository.save(new PostEntity("title", "content"));
        }

        Instant endTime = Instant.now();

        System.out.println("JPA save of " + numberformat.format(size) + " records took: " + Duration.between(startTime, endTime).toMillis() + "ms");

        long count = postJpaRepository.count();

        Assertions.assertEquals(size, count);
    }
}
