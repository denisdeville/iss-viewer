<script setup>

    import {SatelliteInfo} from '~/models/satellite-info'

    const timestamps = [
        "1679083080",
        "1679079420",
        "1679079360",
        "1679079300",
        "1679079240",
    ]

    const { data: satelliteInfos, pending, refresh } = await useFetch<SatelliteInfo>(`http://localhost:8080/iss/sun/25544?timestamps=${timestamps.join(',')}`);
</script>


<template>
    <table v-if="!pending" class="table-fixed">
        <thead>
            <tr>
                <th>Name</th>
                <th>Latitude</th>
                <th>Longitude</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="info in satelliteInfos" >
                <td>{{ info.name }}</td>
                <td>{{ info.latitude }}</td>
                <td>{{ info.longitude }}</td>
            </tr>
        </tbody>
    </table>
</template>