import { createTeamFromJsonObject } from "../entities/team";
import { convertJsonToXml } from "../utils";

class TeamTable {
    columns = ['id', 'first_name', 'last_name', 'strength', 'team_id'];
    urlService;
    constructor(urlService){
        this.urlService = urlService;
    }

    async getAll(requestDto){
        let url = this.urlService.getAllUrl(requestDto.entityName, requestDto.sort, requestDto.filters, requestDto.page, requestDto.pageCount);
        console.log(url);
        let data;
        data = await this.urlService.fetchXmlAsJson(url);
        console.log(`Data from Url Service ${JSON.stringify(data.teamList)}`);
        let teams = []
        if(Object.keys(data.teamList).length == 0){
            return [{}];
        }
        for(const d of data.teamList.children){
            teams.push(createTeamFromJsonObject(d));
        }
        return teams;
    }

    async create(newTeam) {
        let url = this.urlService.getCreateUrl("teams");
        console.log(url);
        let data;
        let teamXml = convertJsonToXml({team: newTeam});
        data = await this.urlService.createItem(url, teamXml);
        console.log(`Data from Url Service ${JSON.stringify(data)}`);
        return data;
    }

    async update(teamJson) {
        let url = this.urlService.getFindByIdUrl("teams", teamJson.id);
        console.log(url);
        let data;
        let teamXml = convertJsonToXml({team: teamJson});
        data = await this.urlService.updateItem(url, teamXml);
        console.log(`Data from Url Service ${JSON.stringify(data)}`);
        if(Object.keys(data).length == 0){
            return {};
        }
        let updatedTeam = createTeamFromJsonObject(data);
        console.log(`Обновлённая Команда: ${JSON.stringify(updatedTeam)}`);
        return updatedTeam;
    }

    async delete(id) {
        let url = this.urlService.getFindByIdUrl("teams", id);
        console.log(url);
        let data;
        data = await this.urlService.deleteItem(url);
        console.log(`Data from Url Service ${JSON.stringify(data)}`);
        if(Object.keys(data).length == 0){
            return {};
        }
        let deletedTeam = createTeamFromJsonObject(data);
        console.log(`Удалённая Команда: ${JSON.stringify(deletedTeam)}`);
        return deletedTeam;
    }


}

export {TeamTable};