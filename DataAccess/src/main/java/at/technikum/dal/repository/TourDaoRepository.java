package at.technikum.dal.repository;

import at.technikum.dal.dao.TourDao;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface TourDaoRepository extends JpaRepository<TourDao, Long> {

    @Transactional
    @Modifying
    @Query("update TourDao t set t.name = ?1, t.description = ?2 where t.id = ?3")
    void updateNameDescriptionById(@NonNull String name, String description, Long id);

    @Transactional
    @Modifying
    @Query("""
            update TourDao t set t.name = ?1, t.start = ?2, t.destination = ?3, t.distance = ?4, t.time = ?5, t.hasTollRoad = ?6, t.hasHighway = ?7, t.transportation = ?8, t.image = ?9, t.description = ?10, t.startLat = ?11, t.startLng = ?12, t.endLat = ?13, t.endLng = ?14
            where t.id = ?15""")
    void updateTourDaoById(String name, String start, String destination, String distance, String time, String hasTollRoad, String hasHighway, String transportation, byte[] image, String description, double startLat, double startLng, double endLat, double endLng, Long id);
}
