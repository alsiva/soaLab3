<template>
  <div>
    <!-- Кнопка для открытия модального окна -->
    <button class="filter-button" @click="openModal">Сортировка</button>

    <!-- Модальное окно -->
    <div v-if="isModalOpen" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <h2>Настройка сортировки</h2>
        
        <!-- Таблица с фильтрами -->
        <form @submit.prevent="applySort">
          <table>
            <tbody>
                <tr v-for="(f, index) in fieldsToSort">
                  <td>
                    {{ f.fieldName }}
                  </td>
                  <td>
                    <input type="radio" :name="'sort-' + index" value="true" v-model="f.asc" @change="handleChange(true, index)">По возрастанию
                  </td>
                  <td>
                    <input type="radio" :name="'sort-' + index" value="false" v-model="f.asc" @change="handleChange(false, index)">По убыванию
                  </td>
                </tr>
            </tbody>
          </table>
          
          <!-- Кнопки для применения и сброса фильтров -->
          <div class="buttons">
            <button type="submit">Применить</button>
            <button type="button" @click="resetSorts">Сбросить</button>
          </div>
        </form>

        <!-- Кнопка закрытия модального окна -->
        <button class="close-button" @click="closeModal">Закрыть</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { Sort } from '@/scripts/dto/dto';


const props = defineProps({
    fieldsToSort: Array
})

const emit = defineEmits(
    ['sortString', 'clearSort']
);

const handleChange = (val, ind) => {
  props.fieldsToSort[ind].asc = val;
}

// Состояние для модального окна
const isModalOpen = ref(false);

// Открытие модального окна
const openModal = () => {
  isModalOpen.value = true;
};

// Закрытие модального окна
const closeModal = () => {
  isModalOpen.value = false;
};

// Применение фильтров (в вашем случае можно отправить на сервер)
const applySort = () => {
  //console.log('Сортировка применена', filters);
  let sortString = Sort.createSortString(props.fieldsToSort);
  console.log(`Sort string: ${sortString}`);
  emit('sortString', sortString);
  closeModal();
};

// Сброс фильтров
const resetSorts = () => {
  for(const s of props.fieldsToSort){
    s.asc = "";
  }
  emit('clearSort');
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
  /*
  width: 400px;
  max-width: 90%;*/
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
