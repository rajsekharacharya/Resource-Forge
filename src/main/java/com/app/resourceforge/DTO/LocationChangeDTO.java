package com.app.resourceforge.DTO;

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
public class LocationChangeDTO {

    public Long assetId;
    public Long locationId;
    public String locationName;
    public Long subLocationId;
    public String subLocationName;

}