<template>
  <div>
    <button class="base-btn" @click="openModal">Создать</button>
    <!-- Модальное окно -->
    <div v-if="isModalOpen" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <h2>Создание нового Элемента</h2>
        
        <!-- Таблица с фильтрами -->
        <form @submit.prevent="applyFilters">
          <table>
            <tbody>
                <tr v-for="(item, index) in fields">
                  <td>
                    {{ item }}
                  </td>
                    <td>
                      <input
                        v-model="newEntity[item]"
                        type="text"
                      />
                    </td>
                </tr>
            </tbody>
          </table>
          <!-- Кнопки для применения и сброса фильтров -->
          <div class="buttons">
            <button type="submit">Применить</button>
          </div>
        </form>

        <!-- Кнопка закрытия модального окна -->
        <button class="close-button" @click="closeModal">Закрыть</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, inject} from 'vue';


const props = defineProps({
    fields : {
      type: Array,
      required: true,
    },
    entity : {
      type: Object,
      required: true,
    }
})

const newEntity = ref({});
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
//const entity = inject('entity');
const applyFilters = () => {
  //emit('update', editableEntity.value);
  console.log(`New Entity: ${JSON.stringify(newEntity.value)}`);
  props.entity.create(newEntity.value);
  closeModal();

}

</script>

<style scoped>
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


.base-btn {
  padding: 10px 20px;
  background-color: #3498db;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
</style>
