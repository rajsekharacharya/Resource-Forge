package com.app.resourceforge.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AssetForProjectDTO {
    
    public boolean type;
    public String project;
    public Long projectId;
    public Long assetId;
    public List<Long> assetIds;
    public String startDate;
    public String endDate;
    public String note;

}
