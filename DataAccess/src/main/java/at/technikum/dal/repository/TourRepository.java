package at.technikum.dal.repository;

import at.technikum.dal.dao.TourDao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<TourDao, Long> {
    @Transactional
    @Modifying
    @Query("""
            update TourDao t set t.name = ?1, t.start = ?2, t.destination = ?3, t.distance = ?4, t.time = ?5, t.hasTollRoad = ?6, t.hasHighway = ?7, t.transportation = ?8, t.image = ?9
            where t.name = ?10""")
    void updateTourDaoByName(@NonNull String name, String start, String destination, String distance, String time, String hasTollRoad, String hasHighway, String transportation, byte[] image, String name1);

    @Transactional
    void deleteByName(String name);
}
