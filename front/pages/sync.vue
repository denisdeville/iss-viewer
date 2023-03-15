<script setup>
    const { data: satelliteInfos } = await useFetch('https://api.wheretheiss.at/v1/satellites/25544', { pick: ['name', 'longitude', 'latitude'] })
</script>
<template>
  <h1>{{ satelliteInfos.name }}</h1>
  <h2>Longitude: {{ satelliteInfos.longitude }}</h2>
  <h2>Latitude{{ satelliteInfos.latitude }}</h2>
</template>