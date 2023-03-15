<template>
    <div id="map"></div>
</template>

<style scoped>
  @import 'ol/ol.css';

  html,
body {
  margin-top: 100px;
  height: 90%;
}

#map {
  position: absolute;
  top: 0;
  bottom: 0;
  width: 100%;
}
</style>

<script>
    import Map from 'ol/Map.js';
    import OSM from 'ol/source/OSM.js';
    import TileLayer from 'ol/layer/Tile.js';
    import View from 'ol/View.js';
    import Feature from 'ol/Feature.js';
    import {Icon, Style} from 'ol/style.js';
    import {Vector as VectorLayer} from 'ol/layer.js';
    import VectorSource from 'ol/source/Vector.js';
    import Point from 'ol/geom/Point.js';
import { VueReactiveUseHeadPlugin } from '@unhead/vue';


    let myOlMap;
    let iconFeature = new Feature({
        geometry: new Point([52000, 52000]),
        name: 'Null Island',
        population: 4000,
        rainfall: 500,
    });


    const iconStyle = new Style({
        image: new Icon({
            anchor: [0.5, 0.5],
            anchorXUnits: 'fraction',
            anchorYUnits: 'pixels',
            src: '/img/iss.jpg',
            scale: 0.05
        }),
    });
    let vectorSource = new VectorSource({
        features: [iconFeature],
    });


    setTimeout(() => {
        

        iconFeature.setStyle(iconStyle);

        const vectorLayer = new VectorLayer({
            source: vectorSource,
        });

        myOlMap = new Map({
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
            projection: 'EPSG:3857'
        }),
      }); 


    //   let secondIconFeature = new Feature({
    //         geometry: new Point([150, 150]),
    //         name: 'Null Island',
    //         population: 4000,
    //         rainfall: 500,
    //         iconStyle: iconStyle
    //     });

    //     vectorSource.addFeature(secondIconFeature);

    }, 500)


    function refreshMap() {
      setTimeout(async () => {
        // refresh();
        // refreshMap();
        const { data: satelliteInfos, pending, refresh } = await useFetch('https://api.wheretheiss.at/v1/satellites/25544');


        // vectorSource.refresh();

        let secondIconFeature = new Feature({
            geometry: new Point([150, 150]),
            name: 'Null Island',
            population: 4000,
            rainfall: 500,
            iconStyle: iconStyle
        });

        vectorSource.clear();

        // vectorSource.addFeature(secondIconFeature);

        // console.log('setCoordinates()');

      }, 2000)
    }
</script>