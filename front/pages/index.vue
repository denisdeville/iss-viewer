<template>
    <header>
    <nav>
      <ul>
        <li><NuxtLink to="/sync">Sync</NuxtLink></li>
        <li><NuxtLink to="/async">Async</NuxtLink></li>
        <li><NuxtLink to="/posts/1">Post 1</NuxtLink></li>
        <li><NuxtLink to="/posts/2">Post 2</NuxtLink></li>
        <li><NuxtLink to="/dashboard">Dashboard</NuxtLink></li>
        <li><NuxtLink to="/login">Login</NuxtLink></li>
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