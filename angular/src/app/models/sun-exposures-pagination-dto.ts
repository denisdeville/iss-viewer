import { SunExposureDto } from "./sun-exposures-dto";

export interface SunExposurePaginationDTO {
    sunExposures: SunExposureDto[];
    pageCount: number;
}