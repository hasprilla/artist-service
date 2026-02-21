package com.sonifoy.artist.infrastructure.adapter.out.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Table("genres")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenreEntity {
    @Id
    private Long id;
    private String name;
}
