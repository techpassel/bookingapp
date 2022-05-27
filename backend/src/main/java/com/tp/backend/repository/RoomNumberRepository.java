package com.tp.backend.repository;

import com.tp.backend.model.RoomNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomNumberRepository extends JpaRepository<RoomNumber, Long> {
}
