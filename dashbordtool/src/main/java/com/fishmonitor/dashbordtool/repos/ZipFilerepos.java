package com.fishmonitor.dashbordtool.repos;

import com.fishmonitor.dashbordtool.models.ZipFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZipFilerepos extends JpaRepository<ZipFileEntity, Long> {
}
