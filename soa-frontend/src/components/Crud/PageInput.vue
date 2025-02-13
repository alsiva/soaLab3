<template>
    <input type="number" class="table__page-input" id="page-input" @input="input" :value="curPageNumber">
</template>

<script setup>

import { ref } from 'vue';

const curPageNumber = ref(0);

const emit = defineEmits(
    ['pageNumber']
);

const input = (e) => {
    if(e.target.value != ""){
        curPageNumber.value = parseInt(e.target.value);
        emit('pageNumber', parseInt(e.target.value));
    }
}

</script>

<style scoped>
.table__page-input{
    position: relative;
    margin: 0 auto;
    width: 50px;
}

</style>