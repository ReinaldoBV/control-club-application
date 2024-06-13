package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.ClubAsserts.*;
import static cl.controlclub.myapp.domain.ClubTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClubMapperTest {

    private ClubMapper clubMapper;

    @BeforeEach
    void setUp() {
        clubMapper = new ClubMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getClubSample1();
        var actual = clubMapper.toEntity(clubMapper.toDto(expected));
        assertClubAllPropertiesEquals(expected, actual);
    }
}
