package shop.donutmarket.donut.domain.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import shop.donutmarket.donut.domain.report.model.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{
    
}
