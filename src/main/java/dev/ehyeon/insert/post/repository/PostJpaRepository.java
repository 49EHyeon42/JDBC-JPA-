package dev.ehyeon.insert.post.repository;

import dev.ehyeon.insert.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {
}
