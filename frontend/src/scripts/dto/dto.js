class RequestAllDto {
    entityName;
    sort = "";
    filters = "";
    page = 1;
    pagesCount = 5;
    constructor(entityName, sort, filters, page, pagesCount){
        this.entityName = entityName;
        this.sort = sort;
        this.filters = filters;
        this.page = page;
        this.pagesCount = pagesCount;
    }
}

class Filter {
    fieldName;
    eq;
    nq;
    gt;
    lt;
    ge;
    le;
    constructor(fieldName, eq="", nq="", gt="", lt="", ge="", le=""){
        this.fieldName = fieldName;
        this.eq = eq;
        this.nq = nq;
        this.gt = gt;
        this.lt = lt;
        this.ge = ge;
        this.le = le;
    }
}

function createFiltersFromArray(filtersArr){
    let filters = [];
    for(const f of filtersArr){
        filters.push(new Filter(f));
    }
    return filters;
}

function createFilterString(filter){
    let filterString = "";
    for(const k of Object.keys(filter)){
        if((filter[k] != "") && (k != "fieldName")){
            filterString += `filter=${filter.fieldName}-${k}-${filter[k]}&`;
        }
    }
    return  filterString.substring(0, filterString.length-1);
}

class Sort {
    constructor(fieldName, asc){
        this.fieldName = fieldName;
        this.asc = asc;
    }

    static sortsArrayFromStrArray(arr){
        let listOfSortObj = [];
        for(const a of arr){
            listOfSortObj.push(new Sort(a, ""));
        }
        return listOfSortObj;
    }
    static createSortString(sortArr){
        let sortStr = "";
        for(let i = 0; i < sortArr.length; i++){
            if(sortArr[i].asc === "") continue;
            if(sortArr[i].asc){
                sortStr += `sort=${sortArr[i].fieldName}`;
            }else{
                sortStr += `sort=-${sortArr[i].fieldName}`;
            }
            sortStr += "&";
        }
        return sortStr.slice(0, -1);
    }
}




export {RequestAllDto, Sort, Filter, createFiltersFromArray, createFilterString};