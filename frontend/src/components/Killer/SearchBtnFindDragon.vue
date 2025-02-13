<template>
    <div class="search-btn">
        <button @click="fillDataContainer">Поиск</button>
    </div>
</template>

<script setup>
import { urlService, urlServiceSecond } from '@/main';
import { rawJsonParser } from '@/scripts/utils';
import { createDragonFromJsonObject } from '@/scripts/entities/dragon';

const props = defineProps({
    minOrMax: String,
})

const emit = defineEmits(
    ['dragonData']
);

const fillDataContainer = async () => {
    console.log(`minOrMax: ${props.minOrMax}`);
    if(props.minOrMax === ''){
        alert('Необходимо выбрать min | max');
        return;
    }
    const base_url = urlServiceSecond.base_url;
    const url = `${base_url}/dragon/find-by-cave-depth/${props.minOrMax}`;
    const rawJson = await urlService.fetchXmlAsJson(url);
    let dragon = createDragonFromJsonObject(rawJson);
    console.log(`Parsed dragon: ${JSON.stringify(dragon)}`);
    /*
    if(Object.keys(data.dragonList).length == 0){
        return [{}];
    }
    console.log(`Data from Url Service ${data}`);
    let dragons = []
    for(const d of data.dragonList.children){
        dragons.push(createDragonFromJsonObject(d));
    }
    console.log(`Fetched Data from second service ${dragons}`)*/
   emit("dragonData", [dragon])
}

</script>

<style scoped>
.search-btn{
    display: flex;
    justify-content: flex-end;
    padding: 10px;
    border-bottom: 1px solid black;
}

</style>