import { Injectable } from '@angular/core';
import Feature from 'ol/Feature';
import Point from 'ol/geom/Point';
import TileLayer from 'ol/layer/Tile';
import Map from 'ol/Map.js';
import OSM from 'ol/source/OSM';
import View from 'ol/View';
import VectorSource from 'ol/source/Vector';
import VectorLayer from 'ol/layer/Vector';
import { OlUtils } from '../utils/ol-utils';


@Injectable({
    providedIn: 'root'
})
export class MapService {

    private _map!: Map;
    private _initialized = false;
    private point: Point = new Point([0, 0]);
    private pointFeature = new Feature();
    private iconStyle = OlUtils.getDefaultIconStyle();

    private _vectorSource!: VectorSource;
    private _vectorLayer = new VectorLayer();

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

        this._map = new Map({
            target: 'map',
            layers: [
              new TileLayer({
                source: new OSM(),
              }), 
              this._vectorLayer
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

    public get map(): Map {
        return this._map;
    }
}
