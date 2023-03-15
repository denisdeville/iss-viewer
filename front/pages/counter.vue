<script setup>
    const counter = useCounter();
</script>

<template>
  <div>
    <button @click="counter++">
      +
    </button>
    <button @click="counter--">
      -
    </button>
  </div>

  <h1>Display</h1>
  <DisplayCounter></DisplayCounter>
</template>