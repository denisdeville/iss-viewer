import { Injectable } from '@angular/core';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import TileLayer from 'ol/layer/Tile';
import Map from 'ol/Map.js' ;
import OSM from 'ol/source/OSM';
import View from 'ol/View';
import VectorSource from 'ol/source/Vector';
import VectorLayer from 'ol/layer/Vector';
import { OlUtils } from '../utils/ol-utils';
import { SatelliteInfos } from '../models/satellite-infos';
import Draw from 'ol/interaction/Draw.js';
import {defaults} from 'ol/interaction/defaults';
import { Observable, Subject } from 'rxjs';
import { DrawnFeaturesService } from './drawn-features.service';
import MultiLineString from 'ol/geom/MultiLineString';
import { LineString } from 'ol/geom';


@Injectable({
    providedIn: 'root'
})
export class MapService {

    private _map!: Map;
    private _initialized = false;
    private point: Point = new Point([0, 0]);
    private pointFeature = new Feature();
    private iconStyle = OlUtils.getDefaultIconStyle();
    private sunIconStyle = OlUtils.getSunIconStyle();

    private _vectorSource!: VectorSource;
    private _vectorLayer = new VectorLayer();

    private _sunExpositionSource!: VectorSource;
    private _sunExpositionVectorLayer = new VectorLayer();

    private _drawSource!: VectorSource;
    private _drawVectorLayer = new VectorLayer();

    private _sunExpositionToFeatureMap: any = {};

    private _draw!: Draw;

    private drawEndSubject = new Subject<Feature>();
    public onDrawEnd$: Observable<Feature> = this.drawEndSubject.asObservable();

    constructor(private drawnFeatureService: DrawnFeaturesService) { }

    public initialize(): void {
        if (this._initialized) {
            return;
        }

        this.pointFeature.setStyle(this.iconStyle);

        this._vectorSource = new VectorSource({
          features: [this.pointFeature]
        });
        this._vectorLayer = new VectorLayer({
          source: this._vectorSource,
        });

        this._sunExpositionSource = new VectorSource({});
        this._sunExpositionVectorLayer = new VectorLayer({
          source: this._sunExpositionSource,
        });

        this._drawSource = new VectorSource();
        this._drawVectorLayer = new VectorLayer({
          source: this._drawSource,
        });

        this._map = new Map({
            target: 'map',
            layers: [
              new TileLayer({
                source: new OSM(),
              }), 
              this._drawVectorLayer,
              this._sunExpositionVectorLayer,
              this._vectorLayer,
            ],
            view: new View({
              center: [0, 0],
              zoom: 2,
              projection: 'EPSG:4326'
            }),
            controls: [],
            interactions: defaults({ doubleClickZoom: false }),
          });
          this._initialized = true;
    }

    public updateSatelliteCoordinates(coordinates: number[]): void {
        this.point.setCoordinates(coordinates);
        if (this.pointFeature.getGeometry() == null) {
            this.pointFeature.setGeometry(this.point);
        }
        this.drawnFeatureService.checkIntersections(this.point);
    }

    public highlightSunExposition(sunExpositions: SatelliteInfos[]): void {
      for(let sunExposition of sunExpositions) {
        let feature = new Feature();
        feature.setStyle(this.sunIconStyle);
  
        feature.setGeometry(new Point([sunExposition.longitude, sunExposition.latitude]));
  
        this._sunExpositionToFeatureMap[sunExposition.timestamp] = feature;
  
        this._sunExpositionSource.addFeature(feature);
      }
    }

    public zoomToCoordinates(coordinates: number[]) {
      this.map.getView().setCenter(coordinates);
      this.map.getView().setZoom(6);
    }

    public zoomTofeature(feature: Feature) {
      const extent = feature.getGeometry()?.getExtent();
      if (extent) {
        this.map.getView().fit(extent);
      }
    }

    public get map(): Map {
        return this._map;
    }

    public activateDraw() {
      this._draw = new Draw({
        source: this._drawSource,
        type: 'Polygon',
      });
      this._map.addInteraction(this._draw);

      this._draw.on('drawend', (event) => {
        this.drawEndSubject.next(event.feature);
      })
    }

    public deactivateDraw() {
      this._map.removeInteraction(this._draw);
    }

    public deleteDrawnFeature(feature: Feature) {
      this._drawSource.removeFeature(feature);
    }

    public addMultiline(coordinates: number[][]): void {

      console.log('addMultiline', coordinates);

      const lineStringFeature = this.createFeature(coordinates);

      this._sunExpositionSource.addFeature(lineStringFeature);

      this.zoomTofeature(lineStringFeature);
    }

    private createFeature(points: number[][]) {
      var pointsSplitted = [];
      var pointsArray = [];
      pointsSplitted.push(points[0]);
      var lastLambda = points[0][0];
    
      for (var i = 1; i < points.length; i++) {
        var lastPoint = points[i - 1];
        var nextPoint = points[i];
        if (Math.abs(nextPoint[0] - lastLambda) > 180) {
          var deltaX = this.xToValueRange(nextPoint[0] - lastPoint[0]);
          var deltaY = nextPoint[1] - lastPoint[1];
          var deltaXS = this.xToValueRange(180 - nextPoint[0]);
          var deltaYS;
          if (deltaX === 0) {
            deltaYS = 0;
          } else {
            deltaYS = deltaY / deltaX * deltaXS;
          }
          var sign = lastPoint[0] < 0 ? -1 : 1;
          pointsSplitted.push([180 * sign, nextPoint[1] + deltaYS]);
          pointsArray.push(pointsSplitted);
          pointsSplitted = [];
          pointsSplitted.push([-180 * sign, nextPoint[1] + deltaYS]);
        }
        pointsSplitted.push(nextPoint);
        lastLambda = nextPoint[0];
      }
    
      pointsArray.push(pointsSplitted);
      var geom = new MultiLineString(pointsArray);
      var feature = new Feature({
        geometry: geom
      });
      return feature;
    }
    
    private xToValueRange(x: number) {
      if (Math.abs(x) > 180) {
        var sign = x < 0 ? -1 : 1;
        return x - 2 * 180 * sign;
      } else {
        return x;
      }
    }
}
