package at.technikum.dal.repository;

import at.technikum.dal.dao.TourDao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourDaoRepository extends JpaRepository<TourDao, Long> {
    @Transactional
    @Modifying
    @Query("""
            update TourDao t set t.name = ?1, t.start = ?2, t.destination = ?3, t.distance = ?4, t.time = ?5, t.hasTollRoad = ?6, t.hasHighway = ?7, t.transportation = ?8, t.image = ?9, t.description = ?10
            where t.id = ?11""")
    void updateTourDaoById(@NonNull String name, String start, String destination, String distance, String time, String hasTollRoad, String hasHighway, String transportation, byte[] image, String description, Long id);

    @Transactional
    @Query(value = "SELECT * FROM tours WHERE (LOWER(description) LIKE LOWER(concat('%', :keyword, '%'))) OR (LOWER(destination) LIKE LOWER(concat('%', :keyword, '%'))) OR (LOWER(distance) LIKE LOWER(concat('%', :keyword, '%'))) OR (LOWER(name) LIKE LOWER(concat('%', :keyword, '%'))) OR (LOWER(start) LIKE LOWER(concat('%', :keyword, '%'))) OR (LOWER(time) LIKE LOWER(concat('%', :keyword, '%'))) OR (LOWER(transportation) LIKE LOWER(concat('%', :keyword, '%')))", nativeQuery = true)
    List<TourDao> findTourIdsByKeyword(@Param("keyword") String keyword);

}
