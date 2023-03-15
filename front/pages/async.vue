<template>
    <!-- you will need to handle a loading state -->
    <div v-if="pending">
      Loading ...
    </div>
    <div v-else>
      <div>
        <li>Name: {{satelliteInfos.name}}</li>
        <li>Lat: {{satelliteInfos.latitude}}</li>
        <li>Lon: {{satelliteInfos.longitude}}</li>
      </div>
    </div>
  </template>
  <script setup>
  const { pending, data: satelliteInfos } = useLazyFetch("https://api.wheretheiss.at/v1/satellites/25544")
  watch(satelliteInfos, (newData) => {
    console.log('got new data', newData);
  })
  </script>