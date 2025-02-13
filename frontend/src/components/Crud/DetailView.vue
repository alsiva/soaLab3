<template>
  <div>
    <!-- Модальное окно -->
    <div v-if="isModalOpen" class="modal-overlay" @click="closeModal">
      <div class="modal-content" @click.stop>
        <h2>Настройка фильтров</h2>
        
        <!-- Таблица с фильтрами -->
        <form @submit.prevent="applyFilters">
          <table>
            <tbody>
                <tr v-for="(value, key) in editableEntity">
                  <td>
                    {{ key }}
                  </td>
                    <td>
                      <input
                        v-model="editableEntity[key]"
                        type="text"
                        :readonly="key === 'id'"
                      />
                    </td>
                </tr>
            </tbody>
          </table>
          <!-- Кнопки для применения и сброса фильтров -->
          <div class="buttons">
            <button type="submit">Применить</button>
            <button type="button" @click="resetState">Сбросить</button>
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
import { ref, watch, inject} from 'vue';


const props = defineProps({
    isActive : {
      type: Boolean,
      required: true,
    },
    curEntity : {
      type: Object
    }
})
// Состояние для модального окна
const isModalOpen = ref(false);

const editableEntity = ref({});
const initialEntityState = ref({});

watch(() => props.isActive, (newVal) => {
  isModalOpen.value=newVal;
  console.log(`Полученный Объект: ${JSON.stringify(props.curEntity)}`);
  editableEntity.value = JSON.parse(JSON.stringify(props.curEntity));
  initialEntityState.value = JSON.parse(JSON.stringify(props.curEntity));;
})

//const fieldFilters = ref(props.fieldsToFilter);

const emit = defineEmits(
    ['windowClose', 'update']
);

// Открытие модального окна
const openModal = () => {
  isModalOpen.value = true;
};

// Закрытие модального окна
const closeModal = () => {
  isModalOpen.value = false;
  emit('windowClose');
};
const resetState = () => {
  editableEntity.value = initialEntityState.value;
  console.log(`Reset Fields : ${JSON.stringify(editableEntity.value)}`);
  console.log(`CurEntity : ${JSON.stringify(props.curEntity)}`);
}

const entity = inject('entity');

const applyFilters = () => {
  //emit('update', editableEntity.value);
  console.log(`Updated Entity: ${JSON.stringify(editableEntity.value)}`);
  //emit('update', editableEntity.value);
  entity.value.update(JSON.parse(JSON.stringify(editableEntity.value)));
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
</style>
