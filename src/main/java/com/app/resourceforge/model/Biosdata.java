package com.app.resourceforge.model;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Biosdata {

    private String smbiosBiosVersion;
    private String manufacturer;
    private String version;
    private String releaseDate;

}
