<script setup>
    const route = useRoute()
    // When accessing /posts/1, route.params.id will be 1
    const postId = route.params.id;
    const { data: count } = postId;
</script>

<template>
    <section>
        <p>This is post {{ count }} ! </p>
    </section>
</template>