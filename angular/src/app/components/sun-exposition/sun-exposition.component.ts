import { Component } from '@angular/core';
import Point from 'ol/geom/Point';
import { SatelliteInfos } from 'src/app/models/satellite-infos';
import { SunExposureDto } from 'src/app/models/sun-exposures-dto';
import { SunExpositionService } from 'src/app/services/sun-exposition.service';
import { TimeUtils } from 'src/app/utils/time-utils';
import { MapService } from '../../services/map.service';

@Component({
  selector: 'app-sun-exposition',
  templateUrl: './sun-exposition.component.html',
  styleUrls: ['./sun-exposition.component.css']
})
export class SunExpositionComponent {

  loading = false;

  private _sunExposures: SunExposureDto[] = [];

  first = 0;

  rows = 10;

  constructor(private mapService: MapService, private sunExpositionService: SunExpositionService) { }

  ngOnInit() {
    this.loadExpositions();
  }

  public onRowSelected(event: any) {
    this.zoomToExpo(event.data)
  }
  
  public onRowUnelected(event: any) {
    this.zoomToExpo(event.data)
  }

  public loadExpositions(): void {
    this.loading = true;
    this.sunExpositionService.getSatelliteSunExposures()
      .subscribe(res => {
        this.loading = false;
        this.sunExposures = this.sunExposures.concat(res);
      });
  }

  zoomToExpo(sunExposition: SunExposureDto) {
    this.mapService.addMultiline(sunExposition.satelliteInfo.map((satelliteInfo) => {
      return [satelliteInfo.longitude, satelliteInfo.latitude];
    }));
  }

  next() {
    this.loadExpositions();
  }

  prev() {
    this.first = this.first - this.rows;
  }

  reset() {
    this.first = 0;
    this.sunExposures = [];
    TimeUtils.resetCurrentTime();
    this.loadExpositions();
  }

  isLastPage(): boolean {
    return false;
  }

  isFirstPage(): boolean {
    return true;
  }

  public get sunExposures(): SunExposureDto[] {
    return this._sunExposures;
  }

  private set sunExposures(value: SunExposureDto[]) {
    this._sunExposures = value;
  }

  public set selectedSatelliteInfos(value: SatelliteInfos[]) {
    
  }
}
