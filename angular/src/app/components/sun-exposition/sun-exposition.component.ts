import { Component } from '@angular/core';
import { SatelliteInfos } from 'src/app/models/satellite-infos';
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

  nowTime = TimeUtils.getInitTime();

  private _expositions: SatelliteInfos[] = [];

  first = 0;

  rows = 10;

  constructor(private mapService: MapService, private sunExpositionService: SunExpositionService) { }

  ngOnInit() {
    this.loadExpositions();
  }

  public onRowSelected(event: any) {
    this.mapService.highlightSunExposition([event.data])
  }

  public onRowUnelected(event: any) {
    this.mapService.removeSunExpositionHighlight([event.data])
  }

  public loadExpositions(): void {
    const timestamps = TimeUtils.getNextTimestamps(10);
    this.loading = true;
    this.sunExpositionService.getSatelliteSunExpositionsForTimestamp(25544, timestamps)
      .subscribe(res => {
        this.loading = false;
        this.expositions = this.expositions.concat(res);
      });
  }

  next() {
    this.loadExpositions();
  }

  prev() {
    this.first = this.first - this.rows;
  }

  reset() {
    this.first = 0;
    this.expositions = [];
    this.mapService.clearSunExpositionHighlight();
    TimeUtils.resetCurrentTime();
    this.loadExpositions();
  }

  isLastPage(): boolean {
    return false;
  }

  isFirstPage(): boolean {
    return true;
  }

  public get expositions(): SatelliteInfos[] {
    return this._expositions;
  }

  private set expositions(value: SatelliteInfos[]) {
    this._expositions = value;
  }

  public set selectedSatelliteInfos(value: SatelliteInfos[]) {
    if (value.length == 0) {
      this.mapService.clearSunExpositionHighlight();
    } else {
      this.mapService.highlightSunExposition(value);
    }
  }
}
