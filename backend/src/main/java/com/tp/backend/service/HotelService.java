package com.tp.backend.service;

import com.tp.backend.dto.HotelRequestDto;
import com.tp.backend.dto.HotelResponseDto;
import com.tp.backend.dto.HotelSearchQueryDto;
import com.tp.backend.enums.PropertyType;
import com.tp.backend.exception.CustomException;
import com.tp.backend.mapper.HotelMapper;
import com.tp.backend.model.Hotel;
import com.tp.backend.model.Room;
import com.tp.backend.repository.HotelRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class HotelService {
    private final FileUploadService fileUploadService;
    private final HotelMapper hotelMapper;
    private final HotelRepository hotelRepository;

    public HotelResponseDto createHotel(HotelRequestDto data) {
        List<MultipartFile> imagesFiles = data.getImagesFiles();
        List<String> images = data.getImagesLinks();
        List<Room> rooms = new ArrayList<>();
        //Here We have stored rooms as empty list. We will implement functionality to add rooms in RoomService.
        Hotel hotel = hotelRepository.save(hotelMapper.mapToModel(data, images, rooms));
        if (imagesFiles.size() > 0) {
            String id = hotel.getId().toString();
            List<String> imagesLinks = imagesFiles.stream().map(e -> fileUploadService
                    .getUploadedFileString("property/" + id, e)).collect(Collectors.toList());
            imagesLinks.addAll(hotel.getImages());
            hotel.setImages(imagesLinks);
            hotel = hotelRepository.save(hotel);
        }
        return hotelMapper.mapToDto(hotel);
    }

    @Transactional
    public String deleteHotel(Long id) {
        try {
            Hotel hotel = hotelRepository.findById(id).orElseThrow(() ->
                    new CustomException("Hotel not found."));
            String imageFolderPrefix = "property/" + id;
            hotelRepository.deleteById(id);
            if (hotel.getImages().size() > 0) {
                fileUploadService.deleteFolderAllFiles(imageFolderPrefix);
            }
            return "Hotel has been deleted.";
        } catch (EmptyResultDataAccessException e) {
            throw new CustomException("Hotel with given id doesn't exist.", e);
        }
    }

    @Transactional
    /*
        If you don't apply @Transactional here you will get following here.
        org.hibernate.HibernateException: Unable to access lob stream
        It is because for 'description' field we have applied @Lob which is used for LongText type data
        As our description can be lengthy hence normal String type won't work for that field,
        And hence used @Lob with that.
    */
    public HotelResponseDto getHotelById(Long id) {
        return hotelMapper.mapToDto(hotelRepository.getById(id));
    }

    public List<HotelResponseDto> searchHotel(HotelSearchQueryDto queryData) {
        int pageNo = queryData.getPageNo();
        int pageSize = queryData.getPageSize();
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        int minPrice = queryData.getMinPrice() != null ? queryData.getMinPrice() : 0;
        int maxPrice = queryData.getMaxPrice() != null ? queryData.getMaxPrice() : 0;
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
                if (maxPrice != 0) {
                    predicates.add(criteriaBuilder.between(root.get("minPrice"), minPrice, maxPrice));
                } else {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("minPrice"), minPrice));
                }
                //For city
                if (queryData.getCity() != null && queryData.getCity() != "") {
                    //predicates.add(criteriaBuilder.equal(root.get("city"), queryData.getCity() ));
                    predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("city")),
                            "%" + queryData.getCity().toLowerCase() + "%"));
                }
                //For Hotel name
                if (queryData.getHotelName() != null && queryData.getHotelName() != "") {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + queryData.getHotelName() + "%"));
                    // We used "equal" in above case as we wanted exact match. Here we want all matching data which
                    // contains the given hotel name.Like if a user search for "Fab" then we should return him all hotels
                    // whose name contains "Fab" like "FabIndia"
                }
                //If needed we can add other conditions also later.
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, pageable);
        return hotelsPage.getContent().stream().map(hotelMapper::mapToDto).collect(Collectors.toList());
    }

    public Map<String, Integer> countByCity(String cityNames) {
        String[] cities = cityNames.split(",");
        Map<String, Integer> hotelsByCity = new HashMap<>();
        for (String city : cities) {
            int count = hotelRepository.countByCityIgnoreCase(city.trim().toLowerCase());
            hotelsByCity.put(city.trim(), count);
        }
        return hotelsByCity;
    }

    public List<Map<String, Object>> countByType() {
        List<Map<String, Object>> responseData = new ArrayList<>();
        Arrays.asList(PropertyType.values()).forEach(e -> {
            responseData.add(createTypeMap(String.valueOf(e), hotelRepository.countByType(e)));
        });
        return responseData;
    }

    public Map<String, Object> createTypeMap(String type, Integer count) {
        //This method is called from countByType() method
        Map<String, Object> typeMap = new HashMap<>();
        typeMap.put("type", type);
        typeMap.put("count", count);
        return typeMap;
    }

    public List<Room> getHotelRooms(Long hotelId) {
        return hotelRepository.getHotelRooms(hotelId);
    }

    @Transactional
    public List<HotelResponseDto> getFeaturedProperties(int responseSize) {
        responseSize = responseSize == 0 ? 10 : responseSize;
        Pageable pageable = PageRequest.of(0, responseSize);
        List<Hotel> hotels = hotelRepository.findByIsFeatured(true, pageable);
        return hotels.stream().map(hotelMapper::mapToDto).collect(Collectors.toList());
    }
}
