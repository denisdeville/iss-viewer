import { Component } from '@angular/core';
import Map from 'ol/Map.js';
import { SatellitePositionService } from '../../services/satellite-position.service';
import { interval, Observable, ReplaySubject, switchMap, takeUntil } from 'rxjs';
import { MapService } from '../../services/map.service';
import { CustomMessagesService } from 'src/app/services/custom-messages.service';
import { IssCoordinates } from 'src/app/models/iss-coordinates';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent {

  map!: Map;
  $currentPosition!: Observable<IssCoordinates>;

  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);

  constructor(private satellitePositionService: SatellitePositionService,
    private mapService: MapService,
    private customMessageService: CustomMessagesService) { }

  public ngOnInit(): void {

    this.mapService.initialize();

    this.satellitePositionService
      .getSatelliteCurrentPosition()
      .pipe(takeUntil(this.destroyed$))
      .subscribe({
        next: newSatelliteData => this.onPositionUpdate(newSatelliteData),
        error: error => this.customMessageService.handleError(error)
      });

    this.updateSatellitePosition();
  }

  public updateSatellitePosition(): void {
    this.$currentPosition = interval(2000).pipe(
      takeUntil(this.destroyed$),
      switchMap(() => this.satellitePositionService.getSatelliteCurrentPosition())
    );

    this.$currentPosition.subscribe({
      next: satelliteInfo => this.onPositionUpdate(satelliteInfo),
      error: error => this.customMessageService.handleError(error)
    }
    );
  }

  private onPositionUpdate(satelliteInfo: IssCoordinates) {
    this.mapService.updateSatelliteCoordinates([satelliteInfo.longitude, satelliteInfo.latitude]);
  }

  ngOnDestroy() {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }
}
