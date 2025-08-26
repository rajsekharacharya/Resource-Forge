package com.app.resourceforge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.app.resourceforge.audit.Auditable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table()
public class ApplicationSetup extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	@Column(name = "sysKey")
	private String key;
	@NotNull
	@Column(name = "sysValue")
	private String value;
	private boolean status;
	@NotNull
	private boolean isDelete;
	@NotNull
	private boolean isEdit;

	
    private Integer superCompanyId;

    private Integer companyId;

	public ApplicationSetup(@NotNull String key,@NotNull String value,boolean status, @NotNull boolean isDelete,
			@NotNull boolean isEdit, Integer superCompanyId, Integer companyId) {
		this.key = key;
		this.value = value;
		this.status = status;
		this.isDelete = isDelete;
		this.isEdit = isEdit;
		this.superCompanyId = superCompanyId;
		this.companyId = companyId;
	}
}
