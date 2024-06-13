package cl.controlclub.myapp.service;

import cl.controlclub.myapp.domain.Club;
import cl.controlclub.myapp.repository.ClubRepository;
import cl.controlclub.myapp.service.dto.ClubDTO;
import cl.controlclub.myapp.service.mapper.ClubMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link cl.controlclub.myapp.domain.Club}.
 */
@Service
@Transactional
public class ClubService {

    private final Logger log = LoggerFactory.getLogger(ClubService.class);

    private final ClubRepository clubRepository;

    private final ClubMapper clubMapper;

    public ClubService(ClubRepository clubRepository, ClubMapper clubMapper) {
        this.clubRepository = clubRepository;
        this.clubMapper = clubMapper;
    }

    /**
     * Save a club.
     *
     * @param clubDTO the entity to save.
     * @return the persisted entity.
     */
    public ClubDTO save(ClubDTO clubDTO) {
        log.debug("Request to save Club : {}", clubDTO);
        Club club = clubMapper.toEntity(clubDTO);
        club = clubRepository.save(club);
        return clubMapper.toDto(club);
    }

    /**
     * Update a club.
     *
     * @param clubDTO the entity to save.
     * @return the persisted entity.
     */
    public ClubDTO update(ClubDTO clubDTO) {
        log.debug("Request to update Club : {}", clubDTO);
        Club club = clubMapper.toEntity(clubDTO);
        club = clubRepository.save(club);
        return clubMapper.toDto(club);
    }

    /**
     * Partially update a club.
     *
     * @param clubDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ClubDTO> partialUpdate(ClubDTO clubDTO) {
        log.debug("Request to partially update Club : {}", clubDTO);

        return clubRepository
            .findById(clubDTO.getId())
            .map(existingClub -> {
                clubMapper.partialUpdate(existingClub, clubDTO);

                return existingClub;
            })
            .map(clubRepository::save)
            .map(clubMapper::toDto);
    }

    /**
     * Get all the clubs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ClubDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Clubs");
        return clubRepository.findAll(pageable).map(clubMapper::toDto);
    }

    /**
     * Get one club by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ClubDTO> findOne(Long id) {
        log.debug("Request to get Club : {}", id);
        return clubRepository.findById(id).map(clubMapper::toDto);
    }

    /**
     * Delete the club by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Club : {}", id);
        clubRepository.deleteById(id);
    }
}
