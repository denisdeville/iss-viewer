import { Component } from '@angular/core';
import Map from 'ol/Map.js';
import { SatelliteInfos } from '../../models/satellite-infos';
import { SatellitePositionService } from '../../services/satellite-position.service';
import { interval, Observable, ReplaySubject, switchMap, takeUntil } from 'rxjs';
import { MapService } from '../../services/map.service';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent {

  map!: Map;
  $currentPosition!: Observable<SatelliteInfos>;
  
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);
  private satelliteId = 25544;

  constructor(private satellitePositionService: SatellitePositionService, private mapService: MapService) { }

  public ngOnInit(): void {

    this.mapService.initialize();

    this.satellitePositionService
      .getSatelliteInfos(this.satelliteId)
      .pipe(takeUntil(this.destroyed$))
      .subscribe(newSatelliteData => this.onPositionUpdate(newSatelliteData));

    this.updateSatellitePosition();
  }

  public updateSatellitePosition(): void {
    this.$currentPosition = interval(5000).pipe(
      takeUntil(this.destroyed$),
      switchMap(() => this.satellitePositionService.getSatelliteInfos(this.satelliteId))
    );

    this.$currentPosition.subscribe(satelliteInfo => this.onPositionUpdate(satelliteInfo));
  }

  private onPositionUpdate(satelliteInfo: SatelliteInfos) {
    this.mapService.highlightPoint([satelliteInfo.longitude, satelliteInfo.latitude]);
  }

  ngOnDestroy() {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }
}
