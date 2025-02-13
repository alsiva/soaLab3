<template>
    <button class="table__search-btn" @click="getEntities">Поиск</button>
</template>

<script setup>
import {ref} from "vue";


const emit = defineEmits(
    ['fetchData']
);

const props = defineProps({
    table : {
        type: Object,
        required: true
    },
    requestDto : {
        type: Object,
        required: true
    }
});
const data = ref(null);
async function getEntities() {
    data.value = await props.table.getAll(props.requestDto);
    console.log(`Данные полученные в SearchBtn: ${data.value}`)
    emit('fetchData', data.value);
}

</script>

<style scoped>

</style>