import { convertXML } from "simple-xml-to-json";
import { rawJsonParser } from "./utils";

class UrlService{
    base_url;
    constructor(base_url){
        this.base_url = base_url;
    }
    // sorts, filters - должны быть строками
    getAllUrl(entityName, sorts, filters, page=1, pageCount=5){
        let url = `${this.base_url}/${entityName}?page=${page}&pageSize=${pageCount}`;
        if(filters != ""){
            url += `&${filters}`;
        }
        if(sorts != ""){
            url += `&${sorts}`;
        }
        return url;
    }

    getFindBySubstringUrl(entityName, substring) {
        let url = `${this.base_url}/${entityName}/search/${substring}`;
        return url;
    }

    getFindByIdUrl(entityName, id){
        let url = `${this.base_url}/${entityName}/${id}`;
        return url;
    }

    getDeleteUrl(entityName, id){
        let url = `${this.base_url}/${entityName}/${id}`;
        return url;
    }

    getUpdateUrl(entityName, id){
        let url = `${this.base_url}/${entityName}/${id}`;
        return url;
    }

    getCreateUrl(entityName){
        let url = `${this.base_url}/${entityName}`;
        return url;
    }

    checkForErrorMessage(jsonData) {
        return null;
        /*return {
            "code": 404,
            "message": "Not found",
            "time": "2023-09-12T00:00:00Z"

        };*/
    }

    async fetchXmlAsJson(url) {

        try {
            // Выполняем GET-запрос
            url = url.replace('se.ifmo.ru', '127.0.0.1:8081');
            const response = await fetch(encodeURI(url), {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/xml',
                    'Accept': 'application/xml',
                    'Access-Control-Request-Method': 'GET',
                    'Access-Control-Request-Headers': 'Content-Type, Authorization'

                }
            });

            // Проверяем, успешен ли запрос
            if (!response.ok) {
                let error = {};
                console.log(`Ошибка: firstService недоступен(Статус: ${response.status})`);
                alert(`Ошибка: firstService недоступен(Статус: ${response.status})`);
                rawJsonParser(convertXML(await response.text()), error);
                console.log(error);
                //throw new Error(`Ошибка: ${response.status}`);
                //alert(`Ошибка: Сервис недоступен\nСтатус:${response.status}\nСообщение:${error.message}`);
                return {};
            }

            // Получаем текст ответа (XML)
            const xmlText = await response.text();
            console.log(xmlText);

            // Преобразуем XML в JSON
            const jsonData = convertXML(xmlText);
            //console.log(jsonData.dragonList.children[0].dragon);
            //const jO = JSON.stringify(jsonData.dragonList.children[0].dragon.children[0].id.content);

            // To-Do: Проверять, что прешедший ответ не содержит ошибки: <error>...</error>
            const error = this.checkForErrorMessage(jsonData)
            if(error != null) {
                alert(`Код: ${error.code}\nОшибка: ${error.message}`);
            }
            const jO = JSON.stringify(jsonData);
            console.log(jO);

            // Возвращаем JSON-данные
            return jsonData;
        } catch (error) {
            const match = url.match(/\/(firstService|secondService)\b/);
            alert(`Ошибка: Недоступен: ${match[1]}`);
            console.error('Ошибка при получении или обработке данных:', error);
        }
    }
    async deleteItem(url) {
        url = url.replace('se.ifmo.ru', '127.0.0.1:8081');
        try {
            const response = await fetch(url, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json'
            }
            });
            let data = await response.text();

            if (response.ok) {
            console.log('Элемент успешно удалён');
            }        
            if (!response.ok) {
                let error = {};
                rawJsonParser(convertXML(data), error)
                console.log(error);
                alert(`Ошибка!\nСтатус:${response.status}\nСообщение:${error.message}`);
                return {};
            }
            return convertXML(data);
        } catch (error) {
            console.error('Произошла ошибка:', error);
        }
    }

    async updateItem(url, xmlContent) {
        url = url.replace('se.ifmo.ru', '127.0.0.1:8081');
        console.log(`Xml Content to send: ${xmlContent}`)
        try {
            const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/xml',
                'Accept': 'application/xml'
            },
            body: xmlContent,
            });
            let data = await response.text();

            if (response.ok) {
            console.log('Элемент успешно удалён');
            }        
            if (!response.ok) {
                let error = {};
                rawJsonParser(convertXML(data), error)
                console.log(error);
                alert(`Ошибка!\nСтатус:${response.status}\nСообщение:${error.message}`);
                return {};
            }
            return convertXML(data);
        } catch (error) {
            console.error('Произошла ошибка:', error);
        }
    }

    async createItem(url, xmlContent) {
        url = url.replace('se.ifmo.ru', '127.0.0.1:8081');
        console.log(`Xml Content to send: ${xmlContent}`)
        try {
            const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/xml',
                'Accept': 'application/xml'
            },
            body: xmlContent,
            });
            let data = await response.text();

            if (response.ok) {
            console.log('Элемент успешно создан');
            }        
            if (!response.ok) {
                let error = {};
                rawJsonParser(convertXML(data), error)
                console.log(error);
                alert(`Ошибка!\nСтатус:${response.status}\nСообщение:${error.message}`);
                return {};
            }
            return convertXML(data);
        } catch (error) {
            console.error('Произошла ошибка:', error);
        }
    }

}

export {UrlService};