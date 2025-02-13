import { RequestAllDto } from "../dto/dto";
import { createDragonFromJsonObject, prepareJsonDragonXmlConversion } from "../entities/dragon";

import { convertJsonToXml } from "../utils";


class DragonTable {
    columns = ['id', 'age', 'color', 'coordinate_x', 'coordinate_y', 'creation_date', 'eyes_count', 'name', 'speaking', 'wingspan'];
    urlService;
    constructor(urlService){
        this.urlService = urlService;
    }

    async getAll(requestDto){
        let url = this.urlService.getAllUrl(requestDto.entityName, requestDto.sort, requestDto.filters, requestDto.page, requestDto.pagesCount);
        console.log(url);
        let data;
        /*this.urlService.fetchXmlAsJson(url)
        .then(jsonData => data = jsonData);
        console.log(data);*/
        data = await this.urlService.fetchXmlAsJson(url);
        if(Object.keys(data.dragonList).length == 0){
            return [{}];
        }
        console.log(`Data from Url Service ${data}`);
        let dragons = []
        for(const d of data.dragonList.children){
            dragons.push(createDragonFromJsonObject(d));
        }
        return dragons;
    }
    async findBySubstring(substring) {
        let url = this.urlService.getFindBySubstringUrl("dragons", substring);
        console.log(url);
        let data;
        /*this.urlService.fetchXmlAsJson(url)
        .then(jsonData => data = jsonData);
        console.log(data);*/
        data = await this.urlService.fetchXmlAsJson(url);
        if(Object.keys(data.dragonList).length == 0){
            return [{}];
        }
        console.log(`Data from Url Service ${data}`);
        let dragons = []
        for(const d of data.dragonList.children){
            dragons.push(createDragonFromJsonObject(d));
        }
        return dragons;
    }
    async findById(id) {
        let url = this.urlService.getFindByIdUrl("dragons", id);
        console.log(url);
        let data;
        /*this.urlService.fetchXmlAsJson(url)
        .then(jsonData => data = jsonData);
        console.log(data);*/
        data = await this.urlService.fetchXmlAsJson(url);
        if(Object.keys(data.dragonList).length == 0){
            return [{}];
        }
        console.log(`Data from Url Service ${data}`);
        let dragons = []
        for(const d of data.dragonList.children){
            dragons.push(createDragonFromJsonObject(d));
        }
        return dragons;
    }
    async delete(id) {
        let url = this.urlService.getFindByIdUrl("dragons", id);
        console.log(url);
        let data;
        data = await this.urlService.deleteItem(url);
        console.log(`Data from Url Service ${JSON.stringify(data)}`);
        if(Object.keys(data).length == 0){
            return {};
        }
        let deletedDragon = createDragonFromJsonObject(data);
        console.log(`Удалённый Дракон: ${JSON.stringify(deletedDragon)}`);
        return deletedDragon;
    }

    async update(dragonJson) {
        let url = this.urlService.getFindByIdUrl("dragons", dragonJson.id);
        console.log(url);
        let data;
        let dragonXml = convertJsonToXml(prepareJsonDragonXmlConversion(dragonJson));
        data = await this.urlService.updateItem(url, dragonXml);
        console.log(`Data from Url Service ${JSON.stringify(data)}`);
        if(Object.keys(data).length == 0){
            return {};
        }
        let updatedDragon = createDragonFromJsonObject(data);
        console.log(`Обновлённый Дракон: ${JSON.stringify(updatedDragon)}`);
        return updatedDragon;
    }

    async create(newDragon) {
        let url = this.urlService.getCreateUrl("dragons");
        console.log(url);
        let data;
        let dragonXml = convertJsonToXml(prepareJsonDragonXmlConversion(newDragon));
        data = await this.urlService.createItem(url, dragonXml);
        console.log(`Data from Url Service ${data}`);
        let dragons = []
        return createDragonFromJsonObject(data);
    }
}

export {DragonTable};