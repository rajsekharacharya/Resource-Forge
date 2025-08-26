package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.resourceforge.model.Employees;
import java.util.List;
import java.util.Optional;

public interface EmployeesRepository extends JpaRepository<Employees, Long> {

        List<Employees> findByStatusAndCompanyIdAndSuperCompanyIdOrderByNameAsc(Boolean status, Integer companyId, Integer superCompanyId);


        List<Employees> findByStatusAndDepartmentAndCompanyIdAndSuperCompanyIdOrderByNameAsc(Boolean status, String department,
                        Integer companyId,
                        Integer superCompanyId);

        List<Employees> findByCompanyIdAndSuperCompanyIdOrderByNameAsc(Integer companyId, Integer superCompanyId);

        Optional<Employees> findByEmployeeIdAndCompanyIdAndSuperCompanyId(String employeeId, Integer companyId,
                        Integer superCompanyId);

        boolean existsByEmployeeIdAndCompanyIdAndSuperCompanyId(String employeeId, Integer companyId,
                        Integer superCompanyId);
}
