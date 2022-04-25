package com.biggwang.web.hellojpa;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest
@Rollback(value = false)
@ActiveProfiles(value = "local")
class AlbumRepositoryTest {

    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void save() {
        albumRepository.save(
                Album.builder()
                        .director("스티븐스필버그")
                        .actor("이병헌")
                        .build()
        );
        movieRepository.save(
                Movie.builder()
                        .author("J.K 롤링")
                        .isbn("34340343003")
                        .build()
        );
    }
}