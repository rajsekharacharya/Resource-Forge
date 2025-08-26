package com.app.resourceforge.service.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.app.resourceforge.model.MyUserDetails;
import com.app.resourceforge.model.Supplier;
import com.app.resourceforge.repository.SupplierRepository;
import com.app.resourceforge.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public ResponseEntity<?> getAllSupplier() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(supplierRepository.findByCompanyId(user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getSuppliers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(supplierRepository.findByTypeAndStatusAndCompanyId("supplier", true, user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getServies() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails user = (MyUserDetails) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(supplierRepository.findByTypeAndStatusAndCompanyId("service", true, user.getCompanyId()));
    }

    @Override
    public ResponseEntity<?> getSupplierById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierRepository.findById(id));
    }

@Override
public ResponseEntity<?> createSupplier(@Valid Supplier supplier) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    MyUserDetails user = (MyUserDetails) auth.getPrincipal();

    supplier.setCompanyId(user.getCompanyId());
    supplier.setSuperCompanyId(user.getSuperCompanyId());
    supplier.setStatus(true);

    try {
        if ("both".equals(supplier.getType())) {
            saveSupplierWithType(supplier, "supplier");
            saveSupplierWithType(supplier, "service");
        } else {
            supplierRepository.save(supplier);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    } catch (DataIntegrityViolationException e) {
        // Handle unique constraint violation (SQL error code 23505)
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate entry");
    } catch (Exception e) {
        // Handle other exceptions
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred");
    }
}

private void saveSupplierWithType(Supplier supplier, String type) {
    Supplier sup = new Supplier();
    sup.setType(type);
    sup.setCompanyId(supplier.getCompanyId());
    sup.setSuperCompanyId(supplier.getSuperCompanyId());
    sup.setStatus(true);
    sup.setName(supplier.getName());

    try {
        supplierRepository.save(sup);
    } catch (DataIntegrityViolationException e) {
        // Handle unique constraint violation (SQL error code 23505)
        throw new RuntimeException("Duplicate entry for unique constraint");
    } catch (Exception e) {
        // Handle other exceptions
        throw new RuntimeException("Error occurred while saving supplier");
    }
}


    @Override
    public ResponseEntity<?> updateSupplier(@Valid Supplier supplier) {
        supplierRepository.save(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body("Saved");
    }

    @Override
    public ResponseEntity<?> toggleSupplierStatus(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        Map<Boolean, String> messages = new HashMap<>();
        messages.put(true, "Deactivate");
        messages.put(false, "Activate");

        String message = supplier
                .map(sup -> {
                    sup.setStatus(!sup.isStatus());
                    supplierRepository.save(sup);
                    return messages.get(sup.isStatus());
                })
                .orElse("Not Found");

        return ResponseEntity.status(message.equals("Not Found") ? HttpStatus.BAD_REQUEST : HttpStatus.OK)
                .body(message);
    }

}
