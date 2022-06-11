package com.tp.backend.repository;

import com.tp.backend.enums.PropertyType;
import com.tp.backend.model.Hotel;
import com.tp.backend.model.Room;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {
    /*
    Here we have not extended "PagingAndSortingRepository" but using it in query as JpaRepository internally extend it.
    PagingAndSortingRepository gives us pagination(i.e. limit) and sorting capability i.e By extending
    PagingAndSortingRepository, we get findAll(Pageable pageable) and findAll(Sort sort) methods for paging and sorting
    and we can pass [Pageable pageable] and [Sort sort] as argument in any custom method also which itself will be
    enough for getting result in pageable and sorted form. Note that if you want pagination and sorting both then pass
    "Sort" data as argument while creating "Pageable" object. So you will not have to pass "Pageable" and "sort" both in
    main query like - "findAll(Pageable pageable, Sort sort)". See example of how to use "Pageable" and "Sort" in
    HotelService and UserService.
    We have extended "JpaSpecificationExecutor" as it gives capability of running custom queries.
    Check HotelService "getRelevantHotels" method for uses of these interfaces.
    In PagingAndSortingRepository type repositories by default returns a Page object. A Page object provides lots of
    extra useful information like total number of pages, number of the current page, whether current page is first
    page or last page etc. If you don't need this information then you should declare the return type of such
    queries(in Repository) as "Slice". Search for "PagingAndSortingRepository" interface and "Slice" class for details.
    */

    Integer countByCityIgnoreCase(String city);

    @Query("SELECT count(h) FROM Hotel h WHERE lower(h.city) IN (:cities)")
        //Here cities represents List of cities.And here we have applied "lower()" on "h.city" to make the
        //city case-insensitive. We have already applied toLowerCase on cities in HotelService.So not applying
        //here again on cities.
    Integer countByAllCitiesIgnoreCase(List<String> cities);
    // Created the above one but not using. However left it and didn't delete for future reference.

    Integer countByType(PropertyType type);

    @Query("SELECT h FROM Hotel h JOIN FETCH h.rooms WHERE h.id = (:hotelId)")
    List<Room> getHotelRooms(Long hotelId);

    List<Hotel> findByIsFeatured(Boolean isFeatured, Pageable pageable);
}
