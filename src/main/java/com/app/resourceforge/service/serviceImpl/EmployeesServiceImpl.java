package com.app.resourceforge.service.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.Asset;
import com.app.resourceforge.model.Department;
import com.app.resourceforge.model.Employees;
import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.repository.AssetRepository;
import com.app.resourceforge.repository.DepartmentRepository;
import com.app.resourceforge.repository.EmployeesRepository;
import com.app.resourceforge.service.EmployeesService;

@Service
public class EmployeesServiceImpl implements EmployeesService {

    @Autowired
    EmployeesRepository employeesRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Override
    public ResponseEntity<?> getAllEmployee() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeesRepository.findByCompanyIdAndSuperCompanyIdOrderByNameAsc(user.getCompanyId(),
                        user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getEmployees() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeesRepository.findByStatusAndCompanyIdAndSuperCompanyIdOrderByNameAsc(true, user.getCompanyId(),
                        user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getEmployeeById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeesRepository.findById(id));
    }

    @Override
    public ResponseEntity<?> createEmployee(Employees employee) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        if (employeesRepository.existsByEmployeeIdAndCompanyIdAndSuperCompanyId(employee.getEmployeeId(),
                user.getCompanyId(), user.getSuperCompanyId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Already In the Db");
        } else {
            employee.setCompanyId(user.getCompanyId());
            employee.setSuperCompanyId(user.getSuperCompanyId());
            employee.setStatus(true);
            employeesRepository.save(employee);

            Optional<Department> dept = departmentRepository.findByNameAndCompanyIdAndSuperCompanyId(
                    employee.getDepartment(),
                    user.getCompanyId(), user.getSuperCompanyId());

            if (!dept.isPresent()) {
                Department newDept = new Department();
                newDept.setName(employee.getDepartment());
                newDept.setSuperCompanyId(user.getSuperCompanyId());
                newDept.setCompanyId(user.getCompanyId());
                newDept.setStatus(true);
                departmentRepository.save(newDept);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
        }
    }

    @Override
    public ResponseEntity<?> updateEmployee(Employees employee) {
        employeesRepository.save(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> toggleEmployeeStatus(Long id) {
        Optional<Employees> employee = employeesRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = employee
                .map(emp -> {
                    emp.setStatus(!emp.isStatus());
                    employeesRepository.save(emp);
                    return messages.get(emp.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

    @Override
    public ResponseEntity<?> getEmployeeByEmpId(String empId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(employeesRepository
                .findByEmployeeIdAndCompanyIdAndSuperCompanyId(empId, user.getCompanyId(), user.getSuperCompanyId()));
    }

    @Override
    public ResponseEntity<?> getEmployeesForAssetManagement(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        Optional<Asset> findById = assetRepository.findById(id);
        if (findById.isPresent() && findById.get().getReserveDept()!=null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(employeesRepository.findByStatusAndDepartmentAndCompanyIdAndSuperCompanyIdOrderByNameAsc(true,
                            findById.get().getReserveDept(), user.getCompanyId(),
                            user.getSuperCompanyId()));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(employeesRepository.findByStatusAndCompanyIdAndSuperCompanyIdOrderByNameAsc(true, user.getCompanyId(),
                            user.getSuperCompanyId()));
        }
    }

}
