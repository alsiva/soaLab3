<template>
    <div class="search">
        <input type="text" placeholder="Введите имя дракона" @input="search">
    </div>
</template>

<script setup>

const props = defineProps({
    collection: String
})
const emit = defineEmits(
    ['search']
);
const search = (e) => {
    if(props.collection == "dragons"){
        emit('search', e.target.value);
    }else {
        alert("Поиск по подстроке можно выполнять только для коллекции Драконов")
    }
}

</script>

<style scoped>
.search {
    display: flex;
    width: fit-content;
}

</style>