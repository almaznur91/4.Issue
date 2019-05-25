class Issue {
    constructor(name, description) {
        this.name = name;
        this.description = description;
    }
}

const addForm = document.getElementById('add-form');
const addFormInputName = document.getElementById('add-form-input-name');
const addFormInputDescription = document.getElementById('add-form-input-description');
addForm.addEventListener('submit', (e) => { //
    // e -> объект события
    // e.cancelable = true/false (можно ли отменять)
    e.preventDefault(); // попросить отменить поведение по умолчанию
    if (addFormInputName.value.trim() === '') {
        return;
    }

    const datum = new Issue(addFormInputName.value, addFormInputDescription.value);
    addFormInputName.value = ''; // вычищаем поле ввода
    addFormInputDescription.value = ''; // вычищаем поле ввода

    const xhr = new XMLHttpRequest();
    xhr.open('POST', 'https://spring-boot-issue.herokuapp.com/api/issues');
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.addEventListener('load', () => {
        load();
    });

    xhr.send(JSON.stringify(datum));

});


const searchForm = document.getElementById('search');
const searchInputName = document.getElementById('search-input-name');
searchForm.addEventListener('submit', (e) => { //
    // e -> объект события
    // e.cancelable = true/false (можно ли отменять)
    e.preventDefault(); // попросить отменить поведение по умолчанию
    if (searchInputName.value.trim() === '') {
        return;
    }

    const xhr = new XMLHttpRequest();
    const url = 'https://spring-boot-issue.herokuapp.com/api/issues/search';
    xhr.open('GET', url + '?' + searchInputName.name + '=' + searchInputName.value);
    searchInputName.value = '';

    xhr.setRequestHeader('Content-Type', 'text/plain');

    xhr.addEventListener('load', () => {
        const data = JSON.parse(xhr.responseText);
        const issueTableEl = document.getElementById('tbody');

        for (const child of issueTableEl.children) {
            issueTableEl.removeChild(child);
        }

        for (const datum of data) {
            const trEl = document.createElement('tr');
            const thEl1 = document.createElement('td');
            const thEl2 = document.createElement('td');
            const thEl3 = document.createElement('td');
            const thEl4 = document.createElement('td');
            thEl1.textContent = datum.name;
            thEl2.textContent = datum.description;
            thEl3.textContent = datum.createDate;
            thEl4.textContent = datum.votes;

            issueTableEl.appendChild(trEl);

            trEl.appendChild(thEl1);
            trEl.appendChild(thEl2);
            trEl.appendChild(thEl3);
            trEl.appendChild(thEl4);
        }
    });

    xhr.send();

});


const issueTableEl = document.getElementById('tbody');

function load() {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', 'https://spring-boot-issue.herokuapp.com/api/issues');
    xhr.setRequestHeader('Accept', 'application/json');

    xhr.addEventListener('load', () => {
        const data = JSON.parse(xhr.responseText);

        for (const child of issueTableEl.children) {
            issueTableEl.removeChild(child);
        }

        for (const datum of data) {
            const trEl = document.createElement('tr');
            const thEl1 = document.createElement('td');
            const thEl2 = document.createElement('td');
            const thEl3 = document.createElement('td');
            const thEl4 = document.createElement('td');
            thEl1.textContent = datum.name;
            thEl2.textContent = datum.description;
            thEl3.textContent = datum.createDate;
            thEl4.textContent = datum.votes;

            issueTableEl.appendChild(trEl);

            trEl.appendChild(thEl1);
            trEl.appendChild(thEl2);
            trEl.appendChild(thEl3);
            trEl.appendChild(thEl4);
        }

        console.log(data.length);
    });
    xhr.addEventListener('error', () => {
        console.log('something bad happened');
    });
    xhr.addEventListener('loadend', () => {
        // finally
    });

    xhr.send();
}

load();
