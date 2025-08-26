package com.app.resourceforge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.resourceforge.model.CallLog;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CallLogRepository extends JpaRepository<CallLog, Long> {

        @Query(nativeQuery = true, value = "SELECT a.id,a.assetid, c.employee, d.department,d.image_link, b.type, b.asset_tag_id, b.serial_no, b.model, a.note, a.time_stamp, a.active , a.status,a.assign_to_name,a.dismiss_by_name,a.resolved_by_name,a.reason "
                        +
                        "FROM call_log a " +
                        "JOIN asset b ON (a.assetid = b.id AND a.company_id = b.company_id AND a.super_company_id = b.super_company_id) "
                        +
                        "LEFT JOIN asset_assign_history c ON (b.id = c.asset_id AND c.status = TRUE AND b.company_id = c.company_id AND b.super_company_id = c.super_company_id) "
                        +
                        "LEFT JOIN employees d ON (c.emp_id = d.id AND c.company_id = d.company_id AND c.super_company_id = d.super_company_id) "
                        +
                        "WHERE a.company_id = ?1 AND a.super_company_id = ?2")
        List<Map<String, Object>> getAllLog(Integer companyId, Integer superCompanyId);

        @Query(nativeQuery = true, value = "SELECT a.id,a.assetid, c.employee, d.department,d.image_link, b.type, b.asset_tag_id, b.serial_no, b.model, a.note, a.time_stamp, a.active , a.status,a.assign_to_name,a.dismiss_by_name,a.resolved_by_name,a.reason "
                        +
                        "FROM call_log a " +
                        "JOIN asset b ON (a.assetid = b.id AND a.company_id = b.company_id AND a.super_company_id = b.super_company_id) "
                        +
                        "LEFT JOIN asset_assign_history c ON (b.id = c.asset_id AND c.status = TRUE AND b.company_id = c.company_id AND b.super_company_id = c.super_company_id) "
                        +
                        "LEFT JOIN employees d ON (c.emp_id = d.id AND c.company_id = d.company_id AND c.super_company_id = d.super_company_id) "
                        +
                        "WHERE a.company_id = ?1 AND a.super_company_id = ?2 AND a.active = ?3")
        List<Map<String, Object>> getActiveLog(Integer companyId, Integer superCompanyId,boolean active);

        @Query(nativeQuery = true, value = "SELECT a.id,a.assetid, c.employee, d.department,d.image_link, b.type, b.asset_tag_id, b.serial_no, b.model, a.note, a.time_stamp, a.active , a.status,a.assign_to_name,a.dismiss_by_name,a.resolved_by_name,a.priority "
                        +
                        "FROM call_log a " +
                        "JOIN asset b ON (a.assetid = b.id AND a.company_id = b.company_id AND a.super_company_id = b.super_company_id) "
                        +
                        "LEFT JOIN asset_assign_history c ON (b.id = c.asset_id AND c.status = TRUE AND b.company_id = c.company_id AND b.super_company_id = c.super_company_id) "
                        +
                        "LEFT JOIN employees d ON (c.emp_id = d.id AND c.company_id = d.company_id AND c.super_company_id = d.super_company_id) "
                        +
                        "WHERE a.assign_to=?1 AND a.company_id = ?2 AND a.super_company_id = ?3 AND a.active=true ")
        List<Map<String, Object>> getgetCallLogForEnggLog(Integer EnggId, Integer companyId, Integer superCompanyId);

        Optional<CallLog> findByIdAndCompanyIdAndSuperCompanyId(Long id, Integer companyId, Integer superCompanyId);

        
        @Query(nativeQuery = true, value = "SELECT count(id) FROM assetmanagement.call_log where assign_to = ?1 and status='ASSIGN'")
        Optional<Integer> assignCall(Integer engId);

        @Query(nativeQuery = true, value = "SELECT count(id) FROM assetmanagement.call_log where resolved_by = ?1 and status='RESOLVED'")
        Optional<Integer> resolveCall(Integer engId);

        @Query(nativeQuery = true, value = "SELECT count(id) FROM assetmanagement.call_log where reason_from = ?1 and status='PENDING'")
        Optional<Integer> pendingCall(Integer engId);

        @Query(nativeQuery = true, value = "SELECT count(id) FROM assetmanagement.call_log where dismiss_by = ?1 and status='DISMISS'")
        Optional<Integer> dismissCall(Integer engId);
}
