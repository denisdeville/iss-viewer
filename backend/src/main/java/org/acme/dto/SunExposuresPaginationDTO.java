package org.acme.dto;

import java.util.List;

public class SunExposuresPaginationDTO {
    private List<SunExposuresDTO> sunExposures;
    private int pageCount;

    public SunExposuresPaginationDTO(List<SunExposuresDTO> sunExposures, int pageCount) {
        this.sunExposures = sunExposures;
        this.pageCount = pageCount;
    }

    public List<SunExposuresDTO> getSunExposures() {
        return sunExposures;
    }

    public void setSunExposures(List<SunExposuresDTO> sunExposures) {
        this.sunExposures = sunExposures;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
