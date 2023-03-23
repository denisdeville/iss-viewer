import { SatelliteInfoDto } from "./satellite-info-dto";

export interface SunExposureDto {
    startTimestamp: number;
    endTimestamp: number;
    latitude: number;
    longitude: number;
    satelliteInfo: SatelliteInfoDto[]
}