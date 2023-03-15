<script setup>
    const center = [40, 40]
    const projection = 'EPSG:4326'
    const zoom = 1
    const rotation = 0
    const radius = 40
    const strokeWidth = 10
    const strokeColor = 'red'
    const fillColor = 'white'
    
    const { data: satelliteInfos, pending, refresh } = await useFetch('https://api.wheretheiss.at/v1/satellites/25544');
    

    // function refreshMap() {
    //   setTimeout(() => {
    //     refresh();
    //     refreshMap();
    //   }, 2000)
    // }

    // refreshMap();


</script>

<template>
  <button @click="updateMap">Refresh</button>
  <!-- Longitude: {{ satelliteInfos.longitude }}
  Latitude: {{ satelliteInfos.latitude }} -->
  <ol-map :loadTilesWhileAnimating="true" :loadTilesWhileInteracting="true" style="height:600px">
  
      <ol-view ref="view" :center="center" :rotation="rotation" :zoom="zoom" :projection="projection" />
  
      <ol-tile-layer>
          <ol-source-osm />
      </ol-tile-layer>
  
      <ol-vector-layer>
          <ol-source-vector>
              <ol-feature>
                  <ol-geom-point v-if="satelliteInfos != null && satelliteInfos.longitude != null && satelliteInfos.latitude != null" :coordinates="[satelliteInfos.longitude, satelliteInfos.latitude]"></ol-geom-point>
                  <ol-style>
                      <ol-style-circle :radius="radius">
                          <ol-style-fill :color="fillColor"></ol-style-fill>
                          <ol-style-stroke :color="strokeColor" :width="strokeWidth"></ol-style-stroke>
                      </ol-style-circle>
                  </ol-style>
              </ol-feature>
  
          </ol-source-vector>
  
      </ol-vector-layer>
  
  </ol-map>
  </template>

<style scoped>
@import 'vue3-openlayers/dist/vue3-openlayers.css';
</style>