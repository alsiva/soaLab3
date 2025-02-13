import { rawJsonParser } from "../utils";

class Dragon {
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
    return new Dragon(id, age, color, coordinate_x, coordinate_y, creation_date, eyes_count, name, speaking, wingspan);
}

function createDragonFromJsonObject(jsonObject){
    console.log(`Данные, полученные от dragonTable: ${JSON.stringify(jsonObject)}`);
    const newDragon = {};
    // После выполнения данной функции объект newDragon будет заполнен
    rawJsonParser(jsonObject, newDragon);
    console.log(JSON.stringify(newDragon));
    return parseJsonValues(newDragon);
}

function prepareJsonDragonXmlConversion(jsonDragon){
    const jsonDragonTemp = jsonDragon;
    let cord_x = jsonDragonTemp.coordinate_x;
    let cord_y = jsonDragonTemp.coordinate_y;
    delete jsonDragonTemp.coordinate_x;
    delete jsonDragonTemp.coordinate_y;
    jsonDragonTemp['coordinates'] = {
        x: cord_x,
        y: cord_y
    };

    let eyes = jsonDragonTemp.eyes_count;
    delete jsonDragonTemp.eyes_count;
    jsonDragonTemp['head'] = {
        eyesCount: eyes
    };
    return {dragon: jsonDragonTemp};
    /*let jsonTemplate = {
        dragon: {
            name: jsonDragon.name,
            coordinates: {
                x: jsonDragon.coordinate_x,
                y: jsonDragon.coordinate_y
            },
            age: jsonDragon.age,
            wingspan: jsonDragon
        }
    }*/
}

export {Dragon, createDragonFromJsonObject, prepareJsonDragonXmlConversion};