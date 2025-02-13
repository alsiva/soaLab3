<template>
    <div class="send-team-container">
        <div class="send-team-container__row">
            <div class="send-team-container__field">
                Отправить команду: <input type="number" placeholder="teamId" v-model="teamId">
            </div>
            <div class="send-team-container__field">
                В пещеру: <input type="number" placeholder="caveId" v-model="caveId">
            </div>
            <button @click="sendTeam">Отправить</button>
            <button @click="reset">Сбросить</button>
        </div>
        <div class="send-team-container__field">
            Результат:
        </div>
        <div class="send-team-container__result">
            {{ result }}
        </div>
    </div>
</template>

<script setup>
import SearchBtnSendTeam from './SearchBtnSendTeam.vue';

import { urlService, urlServiceSecond,  } from '@/main';

import {ref} from 'vue';


const teamId = ref("");
const caveId = ref("");
const result = ref("");

const sendTeam = () => {
    if(teamId.value === "" || caveId.value === ""
        || teamId.value < 0  || caveId.value < 0
    ){
        alert("Поля заполнены некорректно: невозможно отправить команду!");
        return;
    }
    result.value = "Дракон убит!";
}

const reset = () => {
    result.value = "";
}


</script>

<style scoped>
.send-team-container{
    display: flex;
    flex-direction: column;
    gap: 10px;
    padding: 10px;
}
.send-team-container__row{
    display: flex;
    gap: 10px;
}

.send-team-container__result{
    border: 1px solid black;
    min-height: 30px;
    padding: 10px;
}

</style>