<template>
    <header>
    <nav>
      <ul>
        <li><NuxtLink to="/map">Vue 3 map</NuxtLink></li>
        <li><NuxtLink to="/olmap">Classic ol map</NuxtLink></li>
      </ul>
    </nav>
  </header>
    <div>
      <h1>Welcome to the homepage</h1>
      <AppAlert>
        This is an auto-imported component
      </AppAlert>

      <div>
        <img style="width: 200px;" src="/img/iss.jpg" alt="ISS LOGO" />
      </div>
    </div>
  </template>