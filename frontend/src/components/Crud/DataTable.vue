<template>
        <table>
            <thead>
                <tr>
                    <th>Обновить/Удалить</th>
                    <th v-for="c in columns">
                        {{ capitalize(c) }}
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="d in data">
                    <td>
                      <div class="table__buttons">
                        <button class="table__btn table__btn-update" @click="openDetailView(d)">Обновить</button>
                        <button class="table__btn table__btn-delete" @click="$emit('deleteItem', d.id)">Удалить</button>
                      </div>
                    </td>
                    <td v-for="c in columns" @click="openDetailView(d)">
                        {{ d[c] }}
                    </td>
                </tr>
            </tbody>
        </table>
        <DetailView :is-active="active" :curEntity="curEntity" @windowClose="close"/>
</template>

<script setup>

import { capitalize, ref } from 'vue';

import DetailView from './DetailView.vue';


const emit = defineEmits(
    ['deleteItem', 'updateItem']
);

const props = defineProps({
    columns: Array,
    data: Array,
    entity: Object
})

const active = ref(false);
const curEntity = ref({});

const openDetailView = (entity) => {
  console.log("Clicked");
  curEntity.value = entity;
  active.value=true;
}

const close = () => {
  active.value=false;
}

</script>

<style scoped>
table {
  border: 2px solid #42b983;
  border-radius: 3px;
  background-color: #fff;
  width: 100%;
}

th {
  background-color: #42b983;
  color: rgba(255, 255, 255, 0.66);
  cursor: pointer;
  user-select: none;
}

td {
  background-color: #f9f9f9;
}

th,
td {
  min-width: 120px;
  padding: 10px 20px;
}

.table__buttons{
  display: flex;
  flex-direction: column ;
  gap: 10px;
}

.table__btn{
  box-sizing: border-box;
  border-radius: 30px;
  border: none;
  outline: none;
  width: 100px;
  padding: 5px 15px;
}

.table__btn-update{
  background-color: #42b983;
  color: black;
}

.table__btn-delete{
  background-color: red;
  color: black;
}

</style>