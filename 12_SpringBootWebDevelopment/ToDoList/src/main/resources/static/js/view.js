const newTask = document.querySelector("#new-task");
const getTasksButton = document.querySelector("#get-tasks");
const deleteTasksButton = document.querySelector("#delete-tasks");
const createTaskButton = document.querySelector("#create-task");

deleteTasksButton.addEventListener("click", ()=>{
	const promise = deleteAllTasks();
	promise.then(getTasks).then(onTasksReceived);
	});

getTasksButton.addEventListener("click", ()=>{
	const promise = getTasks();
	promise.then(onTasksReceived);
	});

createTaskButton.addEventListener("click", ()=>{
	const promise = createTask(newTask.value);
	promise.then(getTasks).then(onTasksReceived);
	});

function onTasksReceived(tasks){
	const result = document.querySelector('#tasks-result');
	result.innerHTML = '';
		tasks.forEach( el => {
			const tr = document.createElement('tr');
			const td1 = document.createElement('td');
			const td2 = document.createElement('td');
			const td3 = document.createElement('td');
			const deleteButton = document.createElement('button');
			const editButton = document.createElement('button');
			td1.innerHTML = el.id;
			td2.innerHTML = el.title;
			td2.id = el.id;
			td2.width = 300;
			td2.align = "center";
			editButton.innerHTML = "Изменить";
			deleteButton.innerHTML = "Удалить";
			result.appendChild(tr)
			tr.appendChild(td1);
			tr.appendChild(td2);
			tr.appendChild(td3);
			td3.appendChild(editButton);
			td3.appendChild(deleteButton);
			deleteButton.addEventListener("click", ()=>{
				const promise = deleteTask(el.id);
				promise.then(getTasks).then(onTasksReceived);
				});
			editButton.addEventListener("click", ()=>{
				const promise = onEditClick(td2, el);
				});
		});
	}

function onEditClick(td, el){
	td.innerHTML = '';
	const input = document.createElement('input');
	input.type = "text";
	td.appendChild(input);
	input.addEventListener("change", ()=>{
		const promise = updateTask(input.value ,el.id);
		promise.then(getTasks).then(onTasksReceived);
		});
	}