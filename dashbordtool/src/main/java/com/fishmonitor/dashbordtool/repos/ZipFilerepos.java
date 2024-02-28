package com.fishmonitor.dashbordtool.repos;

import com.fishmonitor.dashbordtool.models.ZipFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZipFilerepos extends JpaRepository<ZipFileEntity, Long> {
    @Query(value = "SELECT * FROM file_repo ORDER BY id ASC LIMIT ?1", nativeQuery = true)
    List<ZipFileEntity> findTopNByOrderByIdAsc(int count);

    @Query(value = "SELECT * FROM file_repo ORDER BY id ASC LIMIT 1", nativeQuery = true)
    List<ZipFileEntity> findFirst1ByOrderByIdAsc();
}
