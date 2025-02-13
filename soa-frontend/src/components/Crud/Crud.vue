<template>
    <div class="table-container">
        <div class="table-container__header">
            <CreateBtn :fields="curFields" :entity="entity"/>
            <SortModal :fieldsToSort="sortFields" @sortString="createNewSort" @clearSort="clearSort"/>
            <FiltersModal :fieldsToFilter="fieldsToFilterCurState" @filterStrings="createNewFilter" @clearFilters="clearFilters"/>
            <Search @search="findEntity" :collection="selected_collection"/>
            <label for="collections">Выберите коллекцию:</label>
            <select name="collections" id="collections" v-model="selected_collection">
                <option value="dragons">Dragons</option>
                <option value="teams">Teams</option>
                <option value="hunters">Hunters</option>
            </select>
        </div>
        <div class="table-container_body">
            <DataTable v-if="selected_collection === 'dragons'" :columns="entityColumns"  :data="entityData" @deleteItem="deleteItem"/>
            <DataTable v-else-if="selected_collection === 'hunters'" :columns="entityColumns"  :data="entityData" @deleteItem="deleteItem"/>
            <DataTable v-else-if="selected_collection === 'teams'" :columns="entityColumns"  :data="entityData" @deleteItem="deleteItem"/>
        </div>
        <div class="table__footer">
            <PageInput @pageNumber="updatePageNumber"/>
            <SearchBtn  @fetchData="updateData" :table="entity" :requestDto="reqDto"/>
        </div>
    </div>
</template>

<script setup>
import DataTable from '@/components/Crud/DataTable.vue';
import Search from './Search.vue';
import SearchBtn from './SearchBtn.vue';
import PageInput from './PageInput.vue';
import FiltersModal from './FiltersModal.vue';
import SortModal from './SortModal.vue';
import CreateBtn from './CreateBtn.vue';

import {ref, watch, onMounted, provide} from "vue";

import { DragonTable } from '@/scripts/tables/dragonTable';
import { urlService, urlServiceFirst } from '@/main';
import { createFiltersFromArray, RequestAllDto, Sort } from '@/scripts/dto/dto';
import { HunterTable } from '@/scripts/tables/hunterTable';
import { TeamTable } from '@/scripts/tables/teamTable';

// Состояние текущей коллекции
const selected_collection = ref('dragons');
const entityColumns = ref([]);
const entityData = ref([]);
const entity = ref(new DragonTable(urlServiceFirst));
// Состояние запроса
const reqDto = ref(new RequestAllDto("dragons", "", "", 1, 5));


// Состояния для фильтра
const fieldsToFilterDragons = ["id", "name", "age", "coordinates", "creationdate", "wingspan", "speaking", "color", "eyescount"];
const fieldsToFilterTeams = ["id", "firstName", "lastName", "strength", "teamId"];
const fieldsToFilterHunters = ["id", "name", "power"];
const fieldsToFilterCurState = ref(createFiltersFromArray(fieldsToFilterDragons));
// Создание новых сущностей
const fieldsToCreateDragons = ["id", "name", "age", "coordinate_x", "coordinate_y", "creationdate", "wingspan", "speaking", "color", "eyes_count"]
const fieldsToCreateTeams = ["id", "name", "power"];
const fieldsToCreateHunters = ["id", "firstName", "lastName", "strength", "teamId"];
const curFields = ref(fieldsToCreateDragons);
// Сортировка сущностей
const sortFields = ref(Sort.sortsArrayFromStrArray(fieldsToCreateDragons));

// Provided Values
provide('entity', entity);


onMounted(() => {

})
// Реакция на переключение коллекции
watch(selected_collection, async (new_collection) => {
    reqDto.value.entityName = new_collection;
    reqDto.value.filters = "";
    reqDto.value.sort = "";
    if (new_collection === "dragons") {
        entity.value = new DragonTable(urlServiceFirst);
        fieldsToFilterCurState.value = createFiltersFromArray(fieldsToFilterDragons);
        curFields.value = fieldsToCreateDragons;
        sortFields.value = Sort.sortsArrayFromStrArray(fieldsToCreateDragons);
    } else if(new_collection == "hunters"){
        entity.value = new HunterTable(urlServiceFirst);
        fieldsToFilterCurState.value = createFiltersFromArray(fieldsToFilterHunters);
        curFields.value = fieldsToCreateHunters;
        sortFields.value = Sort.sortsArrayFromStrArray(fieldsToCreateHunters);
    } else if(new_collection == "teams"){
        entity.value = new TeamTable(urlServiceFirst);
        fieldsToFilterCurState.value = createFiltersFromArray(fieldsToFilterTeams);
        curFields.value = fieldsToCreateTeams;
        sortFields.value = Sort.sortsArrayFromStrArray(fieldsToCreateTeams);
    }else {
        alert("Нет такой коллекции!");
    }
    let data = await entity.value.getAll(reqDto.value);
    console.log(`Данные полученные в watch: ${data}`);
    entityData.value = data;
    entityColumns.value = Object.keys(data[0]);
})

const updateTableData = async () => {
    let data = await entity.value.getAll(reqDto.value);
    console.log(`Данные полученные в watch: ${data}`);
    entityData.value = data;
    entityColumns.value = Object.keys(data[0]);
}

const createNewFilter = (filterStrings) => {
    let newFilter = "";
    console.log(`CreateNewFilter: ${filterStrings}`);
    for(const f of filterStrings){
        newFilter += `${f}&`
    }
    newFilter = newFilter.slice(0, -1);
    reqDto.value.filters=newFilter;
    console.log(`Строка фильтрации ${newFilter}`);
}

const clearFilters = () => {
    reqDto.value.filters="";
}
const createNewSort = (sortString) => {
    reqDto.value.sort = sortString;
}

const clearSort = () => {
    reqDto.value.sort = "";
}



const deleteItem = async (id) => {
    let res = await entity.value.delete(id);
    console.log(`RES: ${JSON.stringify(res)}`)
    if(Object.keys(res).length > 0){
        alert(`Элемент с id: ${id} успешно удалён`)
    }
    updateTableData();
}

const updateItem = async (updatedDragon) => {
    let res = await entity.value.update(updatedDragon);
    console.log(`RES: ${JSON.stringify(res)}`)
    if(Object.keys(res).length > 0){
        alert(`Элемент с id: ${id} успешно обновлён`)
    }
    updateTableData();
}

const findEntity = async (search) => {
    let data;
    if(search == ""){
        data = await entity.value.getAll(reqDto.value);
    }else{
        data = await entity.value.findBySubstring(search);
    }
    entityData.value = data;
    entityColumns.value = Object.keys(data[0]);
    console.log(JSON.stringify(data));
}

const updateData = (newData) => {
    //dragon_data.value = newData;
    console.log(`Данные полученные в Crud от SearchBtn: ${newData}`);
    entityData.value = newData;
    console.log(`New Columns: ${Object.keys(newData[0])}`)
    entityColumns.value = Object.keys(newData[0]);
}

const updatePageNumber = async (newPageNumber) => {
    reqDto.value.page = newPageNumber;
    let data = await entity.value.getAll(reqDto.value);
    console.log(`Данные полученные в watch: ${data}`);
    entityData.value = data;
    entityColumns.value = Object.keys(data[0]);

    //await updateTableData(entityData, entityColumns, entity, reqDto);
}

</script>

<style>
    .table-container{
        width: 100%;
        border: 1px solid black;
        overflow-y: auto;
    }
    .table-container__header{
        box-sizing: border-box;
        display: flex;
        gap: 10px;
        border-bottom: 1px solid black;
        justify-content: flex-end;
        align-items: center;
        padding: 10px 30px;
    }
    .table-container_body {
        max-height: 300px;
        overflow-y: auto;
    }
    .table__footer {
        border-top: 1px solid black;
        padding: 10px 10px;
        display: flex;
        justify-content: center;
        gap: 10px;
    }

</style>