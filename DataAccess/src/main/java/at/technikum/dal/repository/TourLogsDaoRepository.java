package at.technikum.dal.repository;

import at.technikum.dal.dao.TourLogsDao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourLogsDaoRepository extends JpaRepository<TourLogsDao, Long> {
    @Transactional
    @Modifying
    @Query("""
            update TourLogsDao t set t.date = ?1, t.duration = ?2, t.comment = ?3, t.difficulty = ?4, t.rating = ?5
            where t.logId = ?6""")
    void updateById(String date, String duration, String comment, Integer difficulty, Float rating, Long logId);

    @Transactional
    @Query(nativeQuery = true, value = "SELECT * FROM logs WHERE tour_id = :tour_id")
    List<TourLogsDao> findByTourId(@Param("tour_id") Long tour_id);


    @SuppressWarnings("all")
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO logs (tour_id, date, duration, comment, difficulty, rating) VALUES (:tour_id, :date, :duration, :comment, :difficulty, :rating) RETURNING log_id")
    Optional<Long> insertTourLog(@Param("tour_id") Long tour_id, @Param("date") String date, @Param("duration") String duration, @Param("comment") String comment, @Param("difficulty") Integer difficulty, @Param("rating") Float rating);
}