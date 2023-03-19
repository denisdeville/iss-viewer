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

    private _sunExpositionToFeatureMap: any = {};

    constructor() { }

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

        this._map = new Map({
            target: 'map',
            layers: [
              new TileLayer({
                source: new OSM(),
              }), 
              this._vectorLayer,
              this._sunExpositionVectorLayer
            ],
            view: new View({
              center: [0, 0],
              zoom: 2,
              projection: 'EPSG:4326'
            }),
            controls: []
          });
          this._initialized = true;
    }

    public highlightPoint(coordinates: number[]): void {
        this.point.setCoordinates(coordinates);
        if (this.pointFeature.getGeometry() == null) {
            this.pointFeature.setGeometry(this.point);
        }
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

    public removeSunExpositionHighlight(sunExposions: SatelliteInfos[]) {
      for(let sunExposition of sunExposions) {
        let feature = this._sunExpositionToFeatureMap[sunExposition.timestamp];
        if (feature){
          this._sunExpositionSource.removeFeature(feature);
        }
      }

      
    }

    public clearSunExpositionHighlight(): void {
      this._sunExpositionSource.clear();
    }

    public get map(): Map {
        return this._map;
    }
}
