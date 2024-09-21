package dev.ehyeon.insert.post.entity;

import dev.ehyeon.insert.base.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class PostEntity extends BaseEntity {

    @Column(nullable = false, length = 32)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String content;
}
