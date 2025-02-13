<template>
    <div class="content-killer">
        <FindDragon/>
        <SendTeam />
    </div>
</template>

<script setup>
import DragonView from './DragonView.vue';
import FindDragon from './FindDragon.vue';
import SearchBtn from './SearchBtnFindDragon.vue';
import SendTeam from './SendTeam.vue';

import {ref} from 'vue';
// Состояния
const minMaxRadioState = ref("");
const dragonData = ref([]);


const columns = ["id", "name", "age", "coordinate_x", "coordinate_y", "creationdate", "wingspan", "speaking", "color", "eyes_count"]


</script>

<style scoped>
.content-killer {
    width: 100%;
    min-height: 150px;
    border: 1px solid black;
}
.dragon-view-content {
    overflow-y: auto;

}

</style>