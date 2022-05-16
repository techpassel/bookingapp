package com.tp.backend.service;

import com.tp.backend.dto.HotelRequestDto;
import com.tp.backend.dto.HotelResponseDto;
import com.tp.backend.dto.HotelSearchQueryDto;
import com.tp.backend.mapper.HotelMapper;
import com.tp.backend.model.Hotel;
import com.tp.backend.repository.HotelRepository;
import exception.BackendException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class HotelService {
    private final FileUploadService fileUploadService;
    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;
    public HotelResponseDto createHotel(HotelRequestDto data){
        List<MultipartFile> imagesFiles = data.getImagesFiles();
        List<String> images = data.getImagesLinks();
        Hotel hotel = hotelRepository.save(hotelMapper.mapToModel(data, images));
        if(images.size() ==0 && imagesFiles.size() > 0){
            String id = hotel.getId().toString();
            List<String> imagesLinks = (List<String>) imagesFiles.stream().map(e -> fileUploadService
                    .getUploadedFileString(id, e));
            hotel.setImages(imagesLinks);
            hotel = hotelRepository.save(hotel);
        }
        return hotelMapper.mapToDto(hotel);
    }

    public String deleteHotel(Long id){
        try {
            hotelRepository.deleteById(id);
            return "Hotel has been deleted.";
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Staff with given id doesn't exist.", e);
        }
    }

    public Hotel getHotelById(Long id){
        return hotelRepository.getById(id);
    }

    public List<Hotel> searchHotel(HotelSearchQueryDto queryData){
        Integer pageNo = queryData.getPageNo();
        Integer pageSize = queryData.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Integer minPrice = queryData.getMinPrice();
        Integer maxPrice = queryData.getMaxPrice();
        /*
            In PagingAndSortingRepository type repository we can send data of type "Pageable" or "Sort" for
            pagination and sorting. Syntax is as follows. Apply suitable one as per your requirement.
            Here "pageNo" is the page number which starts from 0 i.e. use 0 for first page, 1 for 2nd page etc.
            And pageSize is number of results you want in result. For example PageRequest.of(0,10) will return first
            5 data, PageRequest.of(1,10) will return next 10 data etc.
            ////Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            ////Pageable paging = PageRequest.of(pageNo, pageSize);
            ////Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("email"));

            Default sorting type is "descending". If you want data in ascending order the use as follows.
            ////Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("email").ascending());

            If you want only sorting then use Sort instead of Pageable, but note that you will have to use the same
            in repository also.
            ////Sort sortOrder = Sort.by("email");

            If you want to apply sorting on multiple columns or group by sort then you can do that as follows :
            ////Sort emailSort = Sort.by("email");
            ////Sort firstNameSort = Sort.by("first_name");
            ////Sort groupBySort = emailSort.and(firstNameSort);
            ////List<EmployeeEntity> list = repository.findAll(groupBySort);
        */
        // Running custom query with the help of "Specification" clas and "JpaSpecificationExecutor"
        // interface(used in HotelRepository);
        Page<Hotel> hotelsPage = hotelRepository.findAll(new Specification<Hotel>() {
            @Override
            public Predicate toPredicate(Root<Hotel> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                //For max and min price
                if(maxPrice != 0){
                    predicates.add(criteriaBuilder.between(root.get("maxPrice"), minPrice, maxPrice));
                } else {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("minPrice"), minPrice));
                }
                //For city
                if(queryData.getCity() != null && queryData.getCity() != ""){
                    predicates.add(criteriaBuilder.equal(root.get("city"), queryData.getCity() ));
                }
                //For Hotel name
                if(queryData.getHotelName() != null && queryData.getHotelName() != ""){
                    predicates.add(criteriaBuilder.like(root.get("name"), "%"+ queryData.getHotelName() +"%"));
                    // We used "equal" in above case as we wanted exact match. Here we want all matching data which
                    // contains the given hotel name.Like if a user search for "Fab" then we should return him all hotels
                    // whose name contains "Fab" like "FabIndia"
                }
                //If needed we can add other conditions also later.
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
        return hotelsPage.getContent();
    }

    public Long countHotelsByCity(String city){
        return hotelRepository.countByCity(city);
    }
}
