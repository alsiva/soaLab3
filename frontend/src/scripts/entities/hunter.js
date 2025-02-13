
import { rawJsonParser } from "../utils";

class Hunter {
    constructor(id, age, color, coordinate_x, coordinate_y, creation_date, eyes_count, name, speaking, wingspan){
        this.id = id;
        this.age = age;
        this.color = color;
        this.coordinate_x = coordinate_x;
        this.coordinate_y = coordinate_y;
        this.creation_date = creation_date;
        this.eyes_count = eyes_count;
        this.name = name;
        this.speaking = speaking;
        this.wingspan = wingspan;
    }
}

function parseJsonValues(dragon){
    let id = parseInt(dragon.id);
    let age = parseInt(dragon.age);
    let color = dragon.color;
    let coordinate_x = parseFloat(dragon.x);
    let coordinate_y = parseFloat(dragon.y);
    let creation_date = dragon.creationDateRolex;
    let eyes_count = parseInt(dragon.eyesCount);
    let name = dragon.name;
    let speaking = Boolean(dragon.speaking);
    let wingspan = parseInt(dragon.wingspan);
    return new Hunter(id, age, color, coordinate_x, coordinate_y, creation_date, eyes_count, name, speaking, wingspan);
}

function createHunterFromJsonObject(jsonObject){
    console.log(`Данные, полученные от dragonTable: ${JSON.stringify(jsonObject)}`);
    const newHunter = {};
    // После выполнения данной функции объект newDragon будет заполнен
    rawJsonParser(jsonObject, newHunter);
    console.log(JSON.stringify(newHunter));
    //return parseJsonValues(newHunter);
    return newHunter;
}

export {Hunter, createHunterFromJsonObject};