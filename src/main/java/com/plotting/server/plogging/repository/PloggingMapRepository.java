package com.plotting.server.plogging.repository;

import com.plotting.server.plogging.domain.Plogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PloggingMapRepository extends JpaRepository<Plogging, Long> {

    @Query("SELECT p FROM Plogging p WHERE " +
            "(6371 * acos(cos(radians(:lat)) * cos(radians(p.startLatitude)) * " +
            "cos(radians(p.startLongitude) - radians(:lon)) + " +
            "sin(radians(:lat)) * sin(radians(p.startLatitude)))) <= :radius")
    List<Plogging> findAllWithinRadius(@Param("lat") BigDecimal lat,
                                       @Param("lon") BigDecimal lon,
                                       @Param("radius") double radius);


    @Query("SELECT AVG(p.startLatitude), AVG(p.startLongitude), COUNT(p) " +
            "FROM Plogging p WHERE p.startLatitude BETWEEN (:lat - :radius / 111.0) AND (:lat + :radius / 111.0) " +
            "AND p.startLongitude BETWEEN (:lon - (:radius / (111.0 * cos(radians(:lat))))) " +
            "AND (:lon + (:radius / (111.0 * cos(radians(:lat)))))")
    List<Object[]> findClustersInRadius(@Param("lat") BigDecimal lat,
                                        @Param("lon") BigDecimal lon,
                                        @Param("radius") double radius);
}
