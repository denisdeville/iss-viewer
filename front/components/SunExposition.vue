<!-- <script setup>

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
</template> -->

<template>
    <EasyDataTable
      :headers="headers"
      :items="items"
    />
  </template>
  
  <script lang="ts" setup>
  import type { Header, Item } from "vue3-easy-data-table";
  
  const headers: Header[] = [
    { text: "PLAYER", value: "player" },
    { text: "TEAM", value: "team"},
    { text: "NUMBER", value: "number"},
    { text: "POSITION", value: "position"},
    { text: "HEIGHT", value: "indicator.height"},
    { text: "WEIGHT (lbs)", value: "indicator.weight", sortable: true},
    { text: "LAST ATTENDED", value: "lastAttended", width: 200},
    { text: "COUNTRY", value: "country"},
  ];
  
  const items: Item[] = [
    { player: "Stephen Curry", team: "GSW", number: 30, position: 'G', indicator: {"height": '6-2', "weight": 185}, lastAttended: "Davidson", country: "USA"},
    { player: "Lebron James", team: "LAL", number: 6, position: 'F', indicator: {"height": '6-9', "weight": 250}, lastAttended: "St. Vincent-St. Mary HS (OH)", country: "USA"},
    { player: "Kevin Durant", team: "BKN", number: 7, position: 'F', indicator: {"height": '6-10', "weight": 240}, lastAttended: "Texas-Austin", country: "USA"},
    { player: "Giannis Antetokounmpo", team: "MIL", number: 34, position: 'F', indicator: {"height": '6-11', "weight": 242}, lastAttended: "Filathlitikos", country: "Greece"},
  ];
  </script>