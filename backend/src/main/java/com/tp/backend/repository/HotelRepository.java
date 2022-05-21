package com.tp.backend.repository;

import com.tp.backend.enums.PropertyType;
import com.tp.backend.model.Hotel;
import com.tp.backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>,
        PagingAndSortingRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    /*
    Here we have extended "PagingAndSortingRepository" as gives us pagination(i.e. limit) and sorting capability and
    We have extended "JpaSpecificationExecutor" as it gives capability of running custom queries.
    Check HotelService "getRelevantHotels" method for uses of these interfaces.
    In PagingAndSortingRepository type repositories by default returns a Page object. A Page object provides lots of
    extra useful information like total number of pages, number of the current page, whether current page is first
    page or last page etc. If you don't need this information then you should declare the return type of such
    queries(in Repository) as "Slice". Search for "PagingAndSortingRepository" interface and "Slice" class for details.
    */

    Integer countByCity(String city);
    Integer countByType(PropertyType type);

    @Query("SELECT h FROM Hotel h JOIN FETCH h.rooms WHERE h.id = (:hotelId)")
    List<Room> getHotelRooms(Long hotelId);
}
