
const URL = "http://localhost:8080/";
const entitiesNames = ["categorie", "cinema", "film", "place", "projection", "salle", "seance", "ticket", "ville"];
// const titles = ["name", "name", "titre", "numero", "dateProjection", "name", "heureDebut", "id", "name"];
const entities = new Map([
    ["ville", "cinema"],
    ["cinema", "salle"],
    ["salle", ["place", "projection"]],
    ["projection", ["seance", "film", "ticket"]],
]);
const titles = new Map([
    ["ville", "name"],
    ["cinema", "name"],
    ["film", "titre"],
    ["place", "numero"],
    ["projection", "dateProjection"],
    ["salle", "name"],
    ["seance", "heureDebut"],
    ["ticket", "id"],
    ["categorie", "name"]
]);
var editIds = [];

const forms = new Map();

// Functions
const getListItem = (name, modalId, id, entity, parentEntityId) => {
    editIds.push("" + id);
    const listItem = document.createElement("li");
    listItem.setAttribute("id", entity + "" + id);
    listItem.className = "list-group-item d-flex m-2 " + entity;
    listItem.innerHTML = `   
    <p class="lead cursor">${name}</p>
    <ul class="list-inline ml-auto my-0">
        <li class="list-inline-item">
            <button class="btn btn-sm btn-primary modalTrigger" data-toggle="modal" data-target="#${modalId}" modal-form="${modalId}Form" edit-id="${id}">
                <i class="fa fa-eye" aria-hidden="true"></i>
            </button>
            <button class=" btn btn-sm btn-danger" delete-id="${id}">
                <i class="fas fa-trash"></i>
            </button>
        </li>
    </ul>`;

    return listItem;
};

const manageButtons = (entity, editId) => {
    const editButton = document.querySelector(`.${entity}-edit`);
    const cancelButton = document.querySelector(`.${entity}-cancel`);
    const submitButton = document.querySelector(`.${entity}-submit-btn`);
    const formFields = document.getElementsByClassName(entity + "-form-field");
    let formFieldsValues = [];
    editButton.addEventListener("click", (e) => {
        cancelButton.disabled = false;
        for (let index = 0; index < formFields.length; index++) {
            formFields[index].disabled = false;
            formFieldsValues[index] = formFields[index].getAttribute("value");
        }
        submitButton.style.display = "block";
    });
    cancelButton.addEventListener("click", (e) => {
        for (let index = 0; index < formFields.length; index++) {
            formFields[index].disabled = true;
            formFields[index].setAttribute("value", formFieldsValues[index]);
        }
        submitButton.style.display = "none";
        cancelButton.disabled = true;
    });
}

async function getAllData(parentEntity, entity, cardTitle, parentEntityId) {
    let response = null;
    let requestURL;
    if (entity == "ville" || entity == "categorie") {
        requestURL = URL + entity + "s";
        response = await fetch(requestURL);
    }
    else {
        requestURL = parentEntity == "projection" ? `${URL}${parentEntity}s/${parentEntityId}/${entity}` : `${URL}${parentEntity}s/${parentEntityId}/${entity}s`;
        if (parentEntity == "projection" && entity != "ticket") {
            requestURL = `${URL}${parentEntity}s/${parentEntityId}/${entity}`

        }
        else {
            requestURL = `${URL}${parentEntity}s/${parentEntityId}/${entity}s`
        }
        response = await fetch(requestURL);
    }
    var responseData = await response.json();
    const list = document.getElementById(`${entity}sCard`);
    if (parentEntity == "ville")
        list.innerHTML = '';
    var data = [];
    if (entity == "film" || entity == "seance") {
        data[0] = responseData;
    } else {
        data = responseData._embedded[entity + "s"];
    }
    data.forEach(element => {
        let name = element[cardTitle] == "dateProjection" ? element[cardTitle].substring(element[cardTitle].indexOf("T"), element[cardTitle].length) : element[cardTitle];
        let child = getListItem(name, entity + "Modal", element.id, entity);
        list.appendChild(child);
        let childTitle = child.children[0];
        manageButtons(entity, element.id);
        if (entities.has(entity)) {
            childTitle.addEventListener("click", (e) => {
                if (entity == "salle" || entity == "projection") {
                    entities.get(entity).forEach(entityElement => {
                        console.log("Entity element : " + entityElement);
                        getAllData(entity, entityElement, titles.get(entityElement), element.id);
                    });
                }
                else {
                    getAllData(entity, entities.get(entity), titles.get(entity), element.id);
                }
            });
        }
        let childEditButton = child.children[1].children[0].children[0];
        childEditButton.addEventListener("click", (e) => {
            const form = document.getElementById(entity + "ModalForm");
            form.setAttribute("edit-id", element.id);
            form.setAttribute("parent-id", parentEntityId);
            let formFields = document.getElementsByClassName(entity + "-form-field");
            for (let index = 0; index < formFields.length; index++) {
                const field = formFields[index];
                let valueName = field.getAttribute("id");
                valueName = valueName.substring(entity.length, valueName.length);
                field.value = element[valueName.toLowerCase()];
                if (entity == "film") {
                    let src = field.getAttribute("src");
                    field.setAttribute("src", `${src}${element.id}`)
                }
            }
        })
        let childDeleteButton = child.children[1].children[0].children[1];
        let deleteId = childDeleteButton.getAttribute("delete-id");
        childDeleteButton.addEventListener("click", (e) => {
            let result = confirm("Are you sure you want to delete this element?");
            if (result) {
                fetch(URL + "delete" + camelize(entity) + "?id=" + deleteId).then(response => { console.log(response); });
                // window.location.reload();
            }
        });
        manageButtons(entity, element.id);
    });
};

// Events handling
document.addEventListener('DOMContentLoaded', () => {
    getAllData(null, "ville", "name", null);
    getAllData(null, "categorie", "name", null);
    // entitiesNames.forEach(entity => {
    //     manageForms(entity);
    // })
    manageForms();
    const addButtons = document.getElementsByClassName("fa-plus");
    for (let index = 0; index < addButtons.length; index++) {
        const element = addButtons[index];
        element.addEventListener("click", (e) => {
            let entityName = e.target.getAttribute("entity-name");
            let form = document.getElementById(entityName + "ModalForm");
            form.setAttribute("edit-id", -1);
        })
    }
});

const camelize = (string) => {
    string = string.charAt(0).toUpperCase() + string.substring(1, string.length);
    return string;
}

// This function manages forms for the add operation
const manageForms = () => {
    let modalBodies = document.getElementsByClassName("modal-body");
    let forms = [];
    let formData = {};
    for (let index = 0; index < modalBodies.length; index++) {
        const element = modalBodies[index];
        forms.push(element.children[2]);
    }
    forms.forEach(element => {
        element.addEventListener("submit", (e) => {
            const editId = element.getAttribute("edit-id");
            e.preventDefault();
            let entity = e.target.getAttribute("id").substring(0, element.getAttribute("id").indexOf("Modal"));
            const parentId = "";
            if (entity != "ville")
                parentId = element.getAttribute("parent-id");
            const formFields = document.getElementsByClassName(entity + "-form-field");
            switch (entity) {
                case "ville":
                    formData = getVille(formFields, Number.parseInt(editId));
                    break;
                case "cinema":
                    formData = getCinema(formFields, Number.parseInt(parentId), Number.parseInt(editId));
                    break;
                case "salle":
                    formData = getSalle(formFields, Number.parseInt(parentId), Number.parseInt(editId));
                    break;
                case "place":
                    formData = getPlace(formFields, Number.parseInt(parentId), Number.parseInt(editId));
                    break;
                case "projection":
                    formData = getProjection(formFields, Number.parseInt(parentId), Number.parseInt(editId));
                    break;
                case "seance":
                    formData = getSeance(formFields, Number.parseInt(parentId), Number.parseInt(editId));
                    break;
                case "ticket":
                    formData = getTicket(formFields, Number.parseInt(parentId), Number.parseInt(editId));
                    break;
                case "film":
                    formData = getFilm(formFields, Number.parseInt(parentId), Number.parseInt(editId));
                    break;
                case "categorie":
                    formData = getCategorie(formFields, Number.parseInt(parentId), Number.parseInt(editId));
                    break;
                default:
                    break;
            }
            let xhr = new XMLHttpRequest();
            console.log(JSON.stringify(formData));
            xhr.open("POST", URL + "create" + camelize(entity));
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.send(JSON.stringify(formData));
            // window.location.reload();
        })
    });
}

// Form data getters 

const getVille = (formFields, editId) => {
    let ville = {
        id: editId,
        name: formFields[0].value,
        longitude: Number.parseFloat(formFields[1].value),
        latitude: Number.parseFloat(formFields[2].value),
        altitude: Number.parseFloat(formFields[3].value)
    };
    return ville;
}
const getCinema = (formFields, parentId, editId) => {
    let cinema = {
        id: editId,
        name: formFields[0].value,
        longitude: Number.parseFloat(formFields[1].value),
        latitude: Number.parseFloat(formFields[2].value),
        altitude: Number.parseFloat(formFields[3].value),
        villeId: parentId
    };
    return cinema;
}
const getSalle = (formFields, parentId, editId) => {

}
const getPlace = (formFields, parentId, editId) => {

}
const getProjection = (formFields, parentId, editId) => {

}
const getSeance = (formFields, parentId, editId) => {

}
const getTicket = (formFields, parentId, editId) => {

}
const getFilm = (formFields, parentId, editId) => {

}
const getCategorie = (formFields, parentId, editId) => {

}