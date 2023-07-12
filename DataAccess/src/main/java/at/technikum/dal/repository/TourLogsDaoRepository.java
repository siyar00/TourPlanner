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
    @Query("UPDATE TourLogsDao t SET t.date = ?1, t.duration = ?2, t.comment = ?3, t.difficulty = ?4, t.rating = ?5 WHERE t.logId = ?6")
    void updateById(String date, String duration, String comment, Integer difficulty, Float rating, Long logId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE logs SET tour_id = ?1, date = ?2, duration = ?3, comment = ?4, difficulty = ?5, rating = ?6 WHERE log_id = ?7", nativeQuery = true)
    void updateAllById(Long tour_id, String date, String duration, String comment, Integer difficulty, Float rating, Long logId);

    @Transactional
    @Query(nativeQuery = true, value = "SELECT * FROM logs WHERE log_id = :log_id AND tour_id = :tour_id")
    List<TourLogsDao> findByLogsId(@Param("log_id") Long log_id, @Param("tour_id") Long tour_id);

    @Transactional
    @Query(nativeQuery = true, value = "SELECT * FROM logs WHERE tour_id = :tour_id")
    List<TourLogsDao> findByTourId(@Param("tour_id") Long tour_id);

    @SuppressWarnings("all")
    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO logs (tour_id, date, duration, comment, difficulty, rating) VALUES (:tour_id, :date, :duration, :comment, :difficulty, :rating) RETURNING log_id")
    Optional<Long> insertTourLog(@Param("tour_id") Long tour_id, @Param("date") String date, @Param("duration") String duration, @Param("comment") String comment, @Param("difficulty") Integer difficulty, @Param("rating") Float rating);
}