<template>
  <div>
    <!-- Кнопка для открытия модального окна -->
    <button class="filter-button" @click="openModal">Фильтры</button>

    <!-- Модальное окно -->
    <div v-if="isModalOpen" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <h2>Настройка фильтров</h2>
        
        <!-- Таблица с фильтрами -->
        <form @submit.prevent="applyFilters">
          <table>
            <tbody>
                <tr v-for="(f, index) in fieldsToFilter">
                  <td>
                    {{ f.fieldName }}
                  </td>
                  <td>
                    <input type="text" placeholder="=" @input="updateFilter(index, 'eq', f.eq)" v-model="f.eq">
                  </td>
                  <td>
                    <input type="text" placeholder="!=" @input="updateFilter(index, 'nq', f.nq)" v-model="f.nq">
                  </td>
                  <td>
                    <input type="text" placeholder=">" @input="updateFilter(index, 'gt', f.gt)" v-model="f.gt">
                  </td>
                  <td>
                    <input type="text" placeholder="<" @input="updateFilter(index, 'lt', f.lt)" v-model="f.lt">
                  </td>
                  <td>
                    <input type="text" placeholder=">=" @input="updateFilter(index, 'ge', f.ge)" v-model="f.ge">
                  </td>
                  <td>
                    <input type="text" placeholder="<=" @input="updateFilter(index, 'le', f.le)" v-model="f.le">
                  </td>
                </tr>
            </tbody>
          </table>
          
          <!-- Кнопки для применения и сброса фильтров -->
          <div class="buttons">
            <button type="submit">Применить</button>
            <button type="button" @click="resetFilters">Сбросить</button>
          </div>
        </form>

        <!-- Кнопка закрытия модального окна -->
        <button class="close-button" @click="closeModal">Закрыть</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { createFilterString } from '@/scripts/dto/dto';
import { ref, reactive } from 'vue';


const props = defineProps({
    fieldsToFilter : {
      type: Array,
      required: true,
    }
})
// Состояние для модального окна
const isModalOpen = ref(false);

//const fieldFilters = ref(props.fieldsToFilter);

const updateFilter = (index, name, val) => {
  props.fieldsToFilter[index][name] = val;
  console.log(props.fieldsToFilter);
}

const emit = defineEmits(
    ['filterStrings', 'clearFilters']
);

// Открытие модального окна
const openModal = () => {
  isModalOpen.value = true;
};

// Закрытие модального окна
const closeModal = () => {
  isModalOpen.value = false;
};

// Применение фильтров (в вашем случае можно отправить на сервер)
const applyFilters = () => {
  console.log('Фильтры применены:', JSON.stringify(props.fieldsToFilter));
  let filterStrings = []
  for(const f of props.fieldsToFilter){
    let str = createFilterString(f);
    console.log(`ApplyFilters: ${str}`);
    if(str != ""){
      filterStrings.push(str);
    }
  }
  emit('filterStrings', filterStrings);
  closeModal();
};

// Сброс фильтров
const resetFilters = () => {
  for(const f of props.fieldsToFilter){
    for(const t of Object.keys(f)){
      if(t != "fieldName"){
        f[t] = "";
      }
    }
  }
  emit("clearFilters");
};
</script>

<style scoped>
/* Стили для кнопки */
.filter-button {
  padding: 10px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

/* Стили для модального окна */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.buttons {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
}

.close-button {
  margin-top: 20px;
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 10px 15px;
  border-radius: 5px;
  cursor: pointer;
}
</style>
