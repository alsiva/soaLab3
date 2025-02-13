<template>
   <div class="find-dragon-title">
        <div class="find-dragon-title__text">
            Найти дракона живущего в пещере: 
        </div>
        <div class="find-dragon-title__radio-btns">
            <label for="min">
                <input type="radio" name="min-max" id="min" value="min" @change="switchRadioState('min')"> min
            </label>
            <label for="max">
                <input type="radio" name="min-max" id="max" value="max" @change="switchRadioState('max')"> max
            </label>
        </div>
    </div>
    <div class="dragon-view-content">
        <DragonView :columns="columns" :data="dragonData"/>
    </div>
    <SearchBtn :min-or-max="minMaxRadioState" @dragon-data="updateDataState"/>
</template>

<script setup>
import DragonView from './DragonView.vue';
import SearchBtn from './SearchBtnFindDragon.vue';


import {ref} from 'vue';
// Состояния
const dragonData = ref([]);
const minMaxRadioState = ref("");

const columns = ["id", "name", "age", "coordinate_x", "coordinate_y", "creation_date", "wingspan", "speaking", "color", "eyes_count"]

// Обработчики
const switchRadioState = (newState) => {
    minMaxRadioState.value = newState;
    console.log(`New State: ${minMaxRadioState.value}`);
}

const updateDataState = (newDragonData) => {
    dragonData.value = newDragonData;
}

</script>

<style scoped>
.find-dragon-title {
    padding: 10px;
}

.find-dragon-title {
    display: flex;
    justify-content: flex-end;
    gap: 10px;
}

.dragon-view-content {
    overflow-y: auto;

}


</style>