onStart();

function onStart(){
    const promise = getTasks();
    promise.then(onTasksReceived);
}

function onAllTaskDelete(){
    const promise = deleteAllTasks();
    promise.then(onTasksReceived);
}

function onTaskDelete(id){
    const promise = deleteTask(id);
    promise.then(onTasksReceived);
}

function onTaskEdit(id){
        const title = "Введите новое значение";
        result = prompt(title, "");
        const promise = updateTask(result, id);
        promise.then(onTasksReceived);
}

function onTaskCreate(){
    const newTask = document.querySelector("#new-task");
    const promise = createTask(newTask.value);
	promise.then(onTasksReceived);
}

function onTasksReceived(data){
	const result = document.querySelector('body');
	result.innerHTML = "";
	result.insertAdjacentHTML('afterbegin', data);
	}