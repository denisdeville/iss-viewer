import { Component } from '@angular/core';
import Map from 'ol/Map.js';
import OSM from 'ol/source/OSM.js';
import TileLayer from 'ol/layer/Tile.js';
import View from 'ol/View.js';
import { SatelliteInfos } from '../models/satellite-infos';
import { SatellitePositionService } from '../services/satellite-position.service';
import { interval, Observable, ReplaySubject, switchMap, takeUntil } from 'rxjs';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import VectorSource from 'ol/source/Vector';
import VectorLayer from 'ol/layer/Vector';
import { OlUtils } from '../utils/ol-utils';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent {

  map!: Map;
  $currentPosition!: Observable<SatelliteInfos>;
  point: Point = new Point([0, 0]);
  pointFeature = new Feature();

  private iconStyle = OlUtils.getDefaultIconStyle();
  private destroyed$: ReplaySubject<boolean> = new ReplaySubject(1);
  private satelliteId = 25544;

  constructor(private satellitePositionService: SatellitePositionService){}

  public ngOnInit(): void {

    this.intializeMap();

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
    if (this.pointFeature.getGeometry() == null){
      this.pointFeature.setGeometry(this.point);
    }
    this.point.setCoordinates([satelliteInfo.longitude, satelliteInfo.latitude]);
  }

  ngOnDestroy() {
    this.destroyed$.next(true);
    this.destroyed$.complete();
  }

  private intializeMap():void {

    this.pointFeature.setStyle(this.iconStyle);

    let vectorSource = new VectorSource({
      features: [this.pointFeature]
    });
    let vectorLayer = new VectorLayer({
      source: vectorSource,
    });
  
    this.map = new Map({
      target: 'map',
      layers: [
        new TileLayer({
          source: new OSM(),
        }),
        vectorLayer
      ],
      view: new View({
        center: [0, 0],
        zoom: 2,
        projection: 'EPSG:4326'
      }),
      controls: []
    });
  
  }
}
