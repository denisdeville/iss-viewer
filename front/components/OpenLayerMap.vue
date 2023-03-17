<script setup>
    import markerIcon from '/img/iss_marker.png'

    let center = ref(useCenter());
    const projection = useProjection()
    const zoom = 0
    const rotation = 0

    const { data: satelliteInfos, pending, refresh } = await useFetch('http://localhost:8080/iss/position/25544', {pick: ['latitude', 'longitude']});

    function refreshMap() {
      setTimeout(() => {
        refresh();
        refreshMap();
      }, 2000)
    }

    refreshMap();
</script>

<template>
  <ol-map :loadTilesWhileAnimating="true" :loadTilesWhileInteracting="true" style="height:100vh">
  
      <ol-view :center="center" :rotation="rotation" :zoom="zoom" :projection="projection" />
  
      <ol-tile-layer>
          <ol-source-osm />
      </ol-tile-layer>
  
      <ol-vector-layer>
          <ol-source-vector>
              <ol-feature>
                  <ol-geom-point v-if="satelliteInfos != null" :coordinates="[satelliteInfos.longitude, satelliteInfos.latitude]"></ol-geom-point>
                  <ol-style>
                    <ol-style-icon :src="markerIcon" :scale="0.1"></ol-style-icon>
                  </ol-style>
              </ol-feature>
  
          </ol-source-vector>
  
      </ol-vector-layer>
  
  </ol-map>
  </template>

<style scoped>
  @import 'vue3-openlayers/dist/vue3-openlayers.css';
</style>