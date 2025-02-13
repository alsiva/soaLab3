<template>
    <div>
        <table>
            <thead>
                <tr>
                    <th v-for="c in columns">
                        {{ capitalize(c) }}
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="d in data">
                    <td v-for="c in columns" @click="openDetailView(d)">
                        {{ d[c] }}
                    </td>
                </tr>
            </tbody>
        </table>

    </div>
</template>

<script setup>
import {capitalize} from 'vue';

const props = defineProps({
    columns: Array,
    data: Object
})

</script>

<style scoped>
table {
  border: 2px solid #42b983;
  border-radius: 3px;
  background-color: #fff;
  width: 100%;
}

thead{
    min-height: 100px;
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

</style>