package com.fishmonitor.dashbordtool.repos;

import com.fishmonitor.dashbordtool.models.ZipFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZipFilerepos extends JpaRepository<ZipFileEntity, Long> {
}
