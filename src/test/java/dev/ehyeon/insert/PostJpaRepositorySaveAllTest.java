package dev.ehyeon.insert;

import dev.ehyeon.insert.post.entity.PostEntity;
import dev.ehyeon.insert.post.repository.PostJpaRepository;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostJpaRepositorySaveAllTest {

    final NumberFormat numberformat = NumberFormat.getNumberInstance(Locale.US);

    @Autowired
    PostJpaRepository postJpaRepository;

    @Test
    @Transactional
    public void jpaSaveTestWith1KRecords() {
        saveAllTestWithSizeRecords(1_000);
    }

    @Test
    @Transactional
    public void jpaSaveTestWith10KRecords() {
        saveAllTestWithSizeRecords(10_000);
    }

    @Test
    @Transactional
    public void jpaSaveTestWith100KRecords() {
        saveAllTestWithSizeRecords(100_000);
    }

    @Test
    @Transactional
    public void jpaSaveTestWith1MRecords() {
        saveAllTestWithSizeRecords(1_000_000);
    }

    private void saveAllTestWithSizeRecords(int size) {
        Instant startTime = Instant.now();

        List<PostEntity> postEntities = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            postEntities.add(new PostEntity("title", "content"));
        }

        postJpaRepository.saveAll(postEntities);

        Instant endTime = Instant.now();

        System.out.println("JPA save all of " + numberformat.format(size) + " records took: " + Duration.between(startTime, endTime).toMillis() + "ms");

        long count = postJpaRepository.count();

        Assertions.assertEquals(size, count);
    }
}
